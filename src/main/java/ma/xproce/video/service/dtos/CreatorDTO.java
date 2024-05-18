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
    private String email;
    private String profile;
    private List<VideoDTO> videosDTO = new ArrayList<>();
    private List<Creator> friends = new ArrayList<>();
    private List<Creator> requesters = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private List<FriendRequest> friendRequests;


}
