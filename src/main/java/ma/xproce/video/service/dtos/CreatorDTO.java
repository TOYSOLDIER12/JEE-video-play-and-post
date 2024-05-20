package ma.xproce.video.service.dtos;

import lombok.*;
import ma.xproce.video.dao.entity.*;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreatorDTO {
    private Long id;
    private Role role;
    private String username;
    private String mail;
    private String profile;
    private List<VideoDTO> videosDTO = new ArrayList<>();
    private List<CreatorDTO> friends = new ArrayList<>();
    private List<CreatorDTO> requesters = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private List<FriendRequest> friendRequests;


}
