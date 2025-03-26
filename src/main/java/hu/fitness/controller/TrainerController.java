package hu.fitness.controller;

import hu.fitness.dto.*;
import hu.fitness.service.RatingService;
import hu.fitness.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
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

    @PreAuthorize("hasAuthority('PATCH_TRAINER')")
    @CrossOrigin
    @PatchMapping("/{id}")
    @Operation(summary = "Update Trainer by id")
    public TrainerRead updateTrainer(@PathVariable int id, @Valid @RequestBody TrainerUpdate trainerUpdate) {
        return trainerService.updateTrainerSelected(id, trainerUpdate);
    }

    @CrossOrigin
    @GetMapping("/{id}/rating")
    @Operation(summary = "Get average rating of Trainer by id")
    public Double getAverageRating(@PathVariable int id){
        return trainerService.getAverageRating(id);
    }

    @PreAuthorize("hasAuthority('ADD_RATING')")
    @CrossOrigin
    @PostMapping("/{id}/rating")
    @Operation(summary = "Add rating to Trainer by id")
    public void addRating(@PathVariable int id, @RequestBody RatingSave ratingSave) {
        ratingService.addRating(id, ratingSave);
    }

    @PreAuthorize("hasAuthority('UPLOAD_TRAINER_PICTURE')")
    @CrossOrigin
    @PostMapping(value="/upload-picture/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload Trainer's picture")
    public PictureRead uploadPicture(@RequestParam("file") MultipartFile file, @PathVariable Integer id) throws IOException {
        return trainerService.store(file, id);
    }

    @CrossOrigin
    @GetMapping("/picture/{id}")
    @Operation(summary = "Get Trainer's picture by ID")
    public ResponseEntity<byte[]> readPicture(@PathVariable int id) {
        return trainerService.getTrainerPicture(id);
    }




}
