package hu.fitness.service;

import hu.fitness.domain.Rating;
import hu.fitness.domain.Trainer;
import hu.fitness.dto.RatingSave;
import hu.fitness.exception.InvalidRatingInputException;
import hu.fitness.exception.TrainerNotFoundException;
import hu.fitness.repository.RatingRepository;
import hu.fitness.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    public void addRating(Integer trainerId, RatingSave ratingSave) {
        throwExceptionIfTrainerNotFound(trainerId);
        Integer score = ratingSave.getScore();
        if (score<1 || score>5) {
            throw new InvalidRatingInputException();
        }
        Trainer trainer = trainerRepository.getReferenceById(trainerId);
        Rating rating = new Rating();
        rating.setTrainer(trainer);
        rating.setScore(score.doubleValue());
        ratingRepository.save(rating);

        Double averageRating = ratingRepository.getAverageRatingByTrainer(trainerId);
        trainer.setRating(averageRating);
        trainerRepository.save(trainer);
    }

    private void throwExceptionIfTrainerNotFound(int id) {
        if (!trainerRepository.existsById(id)) {
            throw new TrainerNotFoundException();
        }
    }
}
