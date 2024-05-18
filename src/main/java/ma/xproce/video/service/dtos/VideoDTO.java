package ma.xproce.video.service.dtos;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import ma.xproce.video.dao.entity.Comment;
import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.entity.Reaction;
import ma.xproce.video.dao.entity.Video;
import ma.xproce.video.service.mapper.CreatorMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VideoDTO {


    private long id;
    private String name;
    private String url;
    private String description;
    private Date datePublication = Calendar.getInstance().getTime();

    private CreatorDTO creatorDTO;
    private List<Reaction> reactions = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();

}
