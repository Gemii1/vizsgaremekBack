package hu.fitness.dto;


import hu.fitness.enumeration.TrainerUpdateSelected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainerUpdate {

    private TrainerUpdateSelected selected;
    private Object value;
}
