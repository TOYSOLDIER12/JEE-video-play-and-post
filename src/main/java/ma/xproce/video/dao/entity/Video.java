package ma.xproce.video.dao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@ToString
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String url;
    private String description;
    private Date datePublication = Calendar.getInstance().getTime();
    @ManyToOne
    private Creator creator;
    @OneToMany(mappedBy = "video", fetch = FetchType.LAZY)
    private Collection<Reaction> reactions = new ArrayList<>();
    @OneToMany(mappedBy = "video", fetch = FetchType.LAZY)
    private Collection<Comment> comments = new ArrayList<>();
    public void addReaction(Reaction reaction) {
        this.reactions.add(reaction);
    }
    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
}
