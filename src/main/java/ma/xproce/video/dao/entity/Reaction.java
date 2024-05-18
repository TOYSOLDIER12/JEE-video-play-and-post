package ma.xproce.video.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import ma.xproce.video.dao.enumerations.Type;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Type type;
    @ManyToOne
    private Video video;
    @ManyToOne
    private Creator creator;

}
