package hu.fitness.controller;

import hu.fitness.dto.RatingSave;
import hu.fitness.dto.TrainerList;
import hu.fitness.dto.TrainerRead;
import hu.fitness.dto.TrainerSave;
import hu.fitness.service.RatingService;
import hu.fitness.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainer")
@Tag(name = "Trainer Functions", description = "Manage Trainers")
public class TrainerController {

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private RatingService ratingService;


    @CrossOrigin
    @GetMapping("/")
    @Operation(summary = "List all Trainers")
    public List<TrainerList> readTrainerList() {
        return trainerService.listTrainers();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    @Operation(summary = "Get Trainer by id")
    public TrainerRead readTrainer(@PathVariable int id) {
        return trainerService.readTrainer(id);
    }

    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    @Operation(summary = "Create Trainer")
    public TrainerRead createTrainer( @RequestBody TrainerSave trainer) {
        return trainerService.createTrainer(trainer);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Trainer by id")
    public TrainerRead deleteTrainer(@PathVariable int id) {
        return trainerService.deleteTrainer(id);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    @Operation(summary = "Update Trainer by id")
    public TrainerRead updateTrainer(@PathVariable int id, @Valid @RequestBody TrainerSave trainer) {
        return trainerService.updateTrainer(id, trainer);
    }

    @CrossOrigin
    @GetMapping("/{id}/rating")
    @Operation(summary = "Get average rating of Trainer by id")
    public Double getAverageRating(@PathVariable int id){
        return trainerService.getAverageRating(id);
    }

    @CrossOrigin
    @PostMapping("/{id}/rating")
    @Operation(summary = "Add rating to Trainer by id")
    public void addRating(@PathVariable int id, @RequestBody RatingSave ratingSave) {
        ratingService.addRating(id, ratingSave);
    }



}
