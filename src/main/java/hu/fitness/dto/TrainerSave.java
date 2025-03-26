package hu.fitness.dto;

import hu.fitness.enumeration.Gender;
import hu.fitness.enumeration.Qualification;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainerSave {

    @NotNull
    @Min(2)
    @Max(50)
    private String name;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private Gender gender;
    @NotNull
    private Qualification qualification;
    @NotNull
    private String phoneNumber;
    @NotNull
    @Min(0)
    @Max(5)
    private Double rating;
    @NotNull
    private int loginId;
}
