package hu.fitness.service;

import hu.fitness.converter.TrainerConverter;
import hu.fitness.domain.FileEntity;
import hu.fitness.domain.Login;
import hu.fitness.domain.Trainer;
import hu.fitness.dto.*;
import hu.fitness.enumeration.Gender;
import hu.fitness.enumeration.Qualification;
import hu.fitness.exception.*;
import hu.fitness.repository.FileRepository;
import hu.fitness.repository.LoginRepository;
import hu.fitness.repository.RatingRepository;
import hu.fitness.repository.TrainerRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import java.io.IOException;

@Service
public class TrainerService {
    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private FileRepository fileRepository;

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
        Trainer trainer = TrainerConverter.convertSaveToModel(id, trainerSave, login);
        trainerRepository.save(trainer);
        return TrainerConverter.convertModelToRead(trainer);
    }

    public Double getAverageRating(Integer trainerId) {
        if (!trainerRepository.existsById(trainerId)) {
            throw new TrainerNotFoundException();
        }
        return ratingRepository.getAverageRatingByTrainer(trainerId);
    }

    @Transactional
    public TrainerRead updateTrainerSelected(int id, TrainerUpdate trainerUpdate) {
        if (!trainerRepository.existsById(id)) {
            throw new TrainerNotFoundException();
        }
        Trainer trainer = trainerRepository.getReferenceById(id);
        switch (trainerUpdate.getSelected()) {
            case NAME:
                trainer.setName((String) trainerUpdate.getValue());
                break;
            case BIRTH_DATE:
                trainer.setBirthDate(LocalDate.parse((String) trainerUpdate.getValue()));
                break;
            case QUALIFICATION:
                trainer.setQualification(Qualification.valueOf((String) trainerUpdate.getValue()));
                break;
            case PHONE_NUMBER:
                trainer.setPhoneNumber((String) trainerUpdate.getValue());
                break;
            default:
                throw new InvalidInputException();
        }
        trainerRepository.save(trainer);
        return TrainerConverter.convertModelToRead(trainer);
    }

    private void throwExceptionIfTrainerNotFound(int id) {
        if (!trainerRepository.existsById(id)) {
            throw new TrainerNotFoundException();
        }
    }

    @Transactional
    public PictureRead store(MultipartFile file, Integer trainerId) throws IOException {
        if (!trainerRepository.existsById(trainerId)) {
            throw new TrainerNotFoundException();
        }

        Trainer trainer = trainerRepository.getReferenceById(trainerId);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setData(file.getBytes());

        fileEntity = fileRepository.save(fileEntity);
        trainer.setFileEntity(fileEntity);
        trainerRepository.save(trainer);

        PictureRead pictureRead = new PictureRead();
        pictureRead.setId(trainer.getId());
        pictureRead.setFullPath("File stored in DB with ID: " + fileEntity.getId());
        return pictureRead;
    }

    public ResponseEntity<byte[]> getTrainerPicture(Integer trainerId) {
        if (!trainerRepository.existsById(trainerId)) {
            throw new TrainerNotFoundException();
        }

        Trainer trainer = trainerRepository.getReferenceById(trainerId);
        FileEntity fileEntity = trainer.getFileEntity();
        if (fileEntity == null) {
            throw new PictureNotFoundException();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileEntity.getFileType()))
                .body(fileEntity.getData());
    }




    private static String createSavingFileName(MultipartFile file) {
        String fileNameUniquePart = '-' + new SimpleDateFormat("HH-mm-ss").format(new Date()) + '-' + (int)(Math.random() * 1000);
        String fileName = file.getOriginalFilename().split("\\.")[0];
        String fileExtension = file.getOriginalFilename().split("\\.")[1];
        return fileName + fileNameUniquePart + '.' + fileExtension;
    }








}
