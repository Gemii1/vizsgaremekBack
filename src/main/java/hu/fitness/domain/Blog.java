package hu.fitness.domain;

import hu.fitness.enumeration.BlogType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private BlogType blogType;
    private String title;
    private String headerText;
    private String mainText;
    private String image;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
}
