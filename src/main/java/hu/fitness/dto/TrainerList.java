package hu.fitness.dto;

import hu.fitness.domain.Login;
import hu.fitness.enumeration.Gender;
import hu.fitness.enumeration.Qualification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TrainerList {

    private Integer id;
    private String name;
    private LocalDate birthDate;
    private Gender gender;
    private Qualification qualification;
    private String phoneNumber;
    private Double rating;
    private Login login;
}
