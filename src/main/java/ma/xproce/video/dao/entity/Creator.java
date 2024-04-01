package ma.xproce.video.dao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@ToString
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Creator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String profile;
    private String username;
    private String mail;
    private String password;
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private Collection<Video> videos = new ArrayList<>();
    public void addVideo(Video video){
        this.videos.add(video);
    }
}
