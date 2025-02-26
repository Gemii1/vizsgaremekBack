package hu.fitness.dto;

import hu.fitness.enumeration.ClientUpdateSelected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientUpdate {

    private ClientUpdateSelected selected;
    private Object value;
}
