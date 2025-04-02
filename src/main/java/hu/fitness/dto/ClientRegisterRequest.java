package hu.fitness.dto;


import hu.fitness.enumeration.Gender;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientRegisterRequest {
    @NotNull
    @Size(min = 2, max = 50)
    private String name;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private Gender gender;
    @NotNull
    private String phone;
    @NotNull
    private String email;
    @NotNull
    private String password;
}
