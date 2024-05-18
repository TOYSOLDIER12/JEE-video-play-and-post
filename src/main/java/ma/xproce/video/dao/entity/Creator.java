package ma.xproce.video.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import ma.xproce.video.dao.enumerations.Stat;

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
    @ManyToOne (fetch = FetchType.EAGER)
    private Role role;
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private Collection<Video> videos = new ArrayList<>();
    @ManyToMany
    private Collection<Creator> friends = new ArrayList<>();
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private Collection<FriendRequest> friendRequests = new ArrayList<>();

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private Collection<Comment> comments = new ArrayList<>();

    @OneToMany
    private Collection<Creator> requesters = new ArrayList<>();

    @OneToMany (mappedBy = "creator",fetch = FetchType.LAZY)
    private Collection<Reaction> reactions = new ArrayList<>();

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
}
