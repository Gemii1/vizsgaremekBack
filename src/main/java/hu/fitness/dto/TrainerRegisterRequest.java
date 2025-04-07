package hu.fitness.dto;

import hu.fitness.enumeration.Gender;
import hu.fitness.enumeration.Qualification;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainerRegisterRequest {

    @NotNull
    @Size(min = 2, max = 50)
    private String name;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private Gender gender;
    @NotNull
    private Qualification qualification;
    @NotNull
    @Pattern(regexp = "\\d{11}")
    private String phoneNumber;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
}