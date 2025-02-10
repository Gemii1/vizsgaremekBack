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
public class BlogSave {
    private BlogType blogType;
    private String title;
    private String text;
    private String image;
    private Integer trainerId;
}
