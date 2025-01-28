package hu.fitness.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class RatingSave {

    @NotNull
    @Range(min = 1, max = 5)
    private Integer score;
}
