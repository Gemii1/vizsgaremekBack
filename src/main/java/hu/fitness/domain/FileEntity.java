package hu.fitness.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;

    @OneToOne(mappedBy = "fileEntity", cascade = CascadeType.ALL)
    private Trainer trainer;

    public FileEntity(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }
}
