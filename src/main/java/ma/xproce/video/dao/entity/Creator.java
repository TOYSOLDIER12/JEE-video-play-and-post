package ma.xproce.video.dao.entity;

import jakarta.persistence.*;
import lombok.*;


import java.util.ArrayList;

import java.util.List;


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
    @ManyToOne (fetch = FetchType.EAGER)
    private Role role;
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<Video> videos = new ArrayList<>();
    @ManyToMany
    private List<Creator> friends = new ArrayList<>();
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<FriendRequest> friendRequests = new ArrayList<>();

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany
    private List<Creator> requesters = new ArrayList<>();

    @OneToMany (mappedBy = "creator",fetch = FetchType.LAZY)
    private List<Reaction> reactions = new ArrayList<>();

    public void addReaction(Reaction reaction) {
        this.reactions.add(reaction);
    }

    public void addFriend(Creator friend) {
        friends.add(friend);
    }
    public void addFriendRequest(FriendRequest friendRequest) {
        this.friendRequests.add(friendRequest);
    }

    public void addVideo(Video video){
        this.videos.add(video);
    }
    public void addComment(Comment comment) {this.comments.add(comment);}
    public void addRequester(Creator requester) {
        this.requesters.add(requester);
    }
}
