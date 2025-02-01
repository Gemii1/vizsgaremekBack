package hu.fitness.dto;

import hu.fitness.enumeration.ProgramStatus;
import hu.fitness.enumeration.ProgramType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramRead {
    private Integer id;
    private TrainerMinimal trainer;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer price;
    private Integer capacity;
    private ProgramType programType;
    private ProgramStatus status;
}
