package hu.fitness.config;

import hu.fitness.domain.FileEntity;
import hu.fitness.domain.Trainer;
import hu.fitness.exception.TrainerNotFoundException;
import hu.fitness.repository.FileRepository;
import hu.fitness.repository.TrainerRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class ImageLoaderService {

    private final TrainerRepository trainerRepository;

    private final FileRepository fileRepository;

    public ImageLoaderService(TrainerRepository trainerRepository, FileRepository fileRepository) {
        this.trainerRepository = trainerRepository;
        this.fileRepository = fileRepository;
    }

    public void loadImages() {
        Map<Integer, String> trainerImageMap = Map.of(
                101, "edzo1.jpg",
                102, "edzo2.jpg"
        );

        for (Map.Entry<Integer, String> entry : trainerImageMap.entrySet()) {
            Integer trainerId = entry.getKey();
            String imageFileName = entry.getValue();

            try (InputStream inputStream = getClass().getResourceAsStream("/images/" + imageFileName)) {
                byte[] imageBytes = null;
                if (inputStream != null) {
                    imageBytes = IOUtils.toByteArray(inputStream);
                }
                FileEntity file = new FileEntity(imageFileName, "image/jpeg", imageBytes);
                FileEntity savedFile = fileRepository.save(file);

                Optional<Trainer> optionalTrainer = trainerRepository.findById(trainerId);
                if (optionalTrainer.isPresent()) {
                    Trainer trainer = optionalTrainer.get();
                    trainer.setFileEntity(savedFile);
                    trainerRepository.save(trainer);
                } else {
                    throw new TrainerNotFoundException();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}