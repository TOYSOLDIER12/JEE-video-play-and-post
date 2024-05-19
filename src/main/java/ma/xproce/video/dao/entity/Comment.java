package ma.xproce.video.dao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    private LocalDateTime date;
    @ManyToOne
    private Creator creator;
    @ManyToOne
    private Video video;

}
