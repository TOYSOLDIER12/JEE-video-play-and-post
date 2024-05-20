package ma.xproce.video.service.dtos;

import lombok.*;
import ma.xproce.video.dao.entity.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreatorDTOADD {
    private long id;
    private String profile;
    private String username;
    private String mail;
    private String password;
    private Role role;
    private List<VideoDTOADD> videos = new ArrayList<>();
    private List<CreatorDTOADD> friends = new ArrayList<>();
    private List<FriendRequest> friendRequests = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private List<Creator> requesters = new ArrayList<>();
}
