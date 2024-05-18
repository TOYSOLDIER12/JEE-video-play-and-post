package ma.xproce.video.service.dtos;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import ma.xproce.video.dao.entity.Comment;
import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.entity.Reaction;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VideoDTOADD {
    private long id;
    private String name;
    private String url;
    private String description;
    private Date datePublication = Calendar.getInstance().getTime();

    private Creator creator;

    private List<Reaction> reactions = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>();
}
