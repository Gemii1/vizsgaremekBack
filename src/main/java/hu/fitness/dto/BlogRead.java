package hu.fitness.dto;

import hu.fitness.enumeration.BlogType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogRead {
    private Integer id;
    private BlogType blogType;
    private String title;
    private String headerText;
    private String mainText;
    private String image;
    private TrainerMinimal trainer;
}
