package hu.fitness.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ApplicactonExceptionHandler {

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "TRAINER_NOT_FOUND")
    @ExceptionHandler(TrainerNotFoundException.class)
    public void trainerNotFound(TrainerNotFoundException ex) {}

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "LOGIN_NOT_FOUND")
    @ExceptionHandler(LoginNotFoundException.class)
    public void contactNotFound(LoginNotFoundException ex) {}

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "CLIENT_NOT_FOUND")
    @ExceptionHandler(ClientNotFoundException.class)
    public void clientNotFound(ClientNotFoundException ex) {}

    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Must be number (1-5)")
    @ExceptionHandler(RatingNotDoubleException.class)
    public void ratingNotDouble(RatingNotDoubleException ex) {}

    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Must be number (1-5)")
    @ExceptionHandler(InvalidRatingInputException.class)
    public void invalidRatingInputException(InvalidRatingInputException ex) {}
}
