package ma.xproce.video.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import ma.xproce.video.dao.enumerations.Stat;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Creator sender;
    @ManyToOne
    private Creator receiver;
    private Stat status ;

}
