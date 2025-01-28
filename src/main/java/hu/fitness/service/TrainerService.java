package hu.fitness.service;

import hu.fitness.converter.TrainerConverter;
import hu.fitness.domain.Login;
import hu.fitness.domain.Trainer;
import hu.fitness.dto.TrainerList;
import hu.fitness.dto.TrainerRead;
import hu.fitness.dto.TrainerSave;
import hu.fitness.exception.LoginNotFoundException;
import hu.fitness.exception.TrainerNotFoundException;
import hu.fitness.repository.LoginRepository;
import hu.fitness.repository.RatingRepository;
import hu.fitness.repository.TrainerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainerService {
    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private RatingRepository ratingRepository;

    public List<TrainerList> listTrainers() {
        List<TrainerList> trainerList = new ArrayList<>();
        List<Trainer> trainers = trainerRepository.findAll();
        trainerList = TrainerConverter.convertModelsToLists(trainers);
        return trainerList;
    }


    public TrainerRead createTrainer(TrainerSave trainerSave) {
        if (!loginRepository.existsById(trainerSave.getLoginId())) {
            throw new LoginNotFoundException();
        }
        Login login = loginRepository.getReferenceById(trainerSave.getLoginId());
        Trainer trainer = TrainerConverter.convertSaveToModel(trainerSave, login);
        Trainer savedTrainer = trainerRepository.save(trainer);
        return TrainerConverter.convertModelToRead(savedTrainer);
    }

    public TrainerRead readTrainer(Integer id) {
        if (!trainerRepository.existsById(id)) {
            throw new TrainerNotFoundException();
        }
        Trainer trainer = trainerRepository.getReferenceById(id);
        return TrainerConverter.convertModelToRead(trainer);
    }

    public TrainerRead deleteTrainer(int id) {
        if (!trainerRepository.existsById(id)) {
            throw new TrainerNotFoundException();
        }
        TrainerRead trainerRead = TrainerConverter.convertModelToRead(trainerRepository.getReferenceById(id));
        trainerRepository.deleteById(id);
        return trainerRead;
    }

    public TrainerRead updateTrainer(int id, @Valid TrainerSave trainerSave) {
        if (!trainerRepository.existsById(id)) {
            throw new TrainerNotFoundException();
        }
        if (!loginRepository.existsById(trainerSave.getLoginId())) {
            throw new LoginNotFoundException();
        }
        Login login = loginRepository.getReferenceById(trainerSave.getLoginId());
        Trainer trainer = TrainerConverter.convertSaveToModel(id ,trainerSave, login);
        trainerRepository.save(trainer);
        return TrainerConverter.convertModelToRead(trainer);
    }

    public Double getAverageRating(Integer trainerId) {
        if (!trainerRepository.existsById(trainerId)) {
            throw new TrainerNotFoundException();
        }
        return ratingRepository.getAverageRatingByTrainer(trainerId);
    }

    private void throwExceptionIfTrainerNotFound(int id) {
        if (!trainerRepository.existsById(id)) {
            throw new TrainerNotFoundException();
        }
    }
}
