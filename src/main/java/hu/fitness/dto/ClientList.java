package hu.fitness.dto;

import hu.fitness.enumeration.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class ClientList {
    private Integer id;
    private String name;
    private LocalDate birthDate;
    private Gender gender;
    private String phoneNumber;

    private LoginRead login;
}
