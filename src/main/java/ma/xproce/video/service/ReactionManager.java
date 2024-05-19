package ma.xproce.video.service;

import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.entity.Reaction;
import ma.xproce.video.dao.entity.Video;

import java.util.List;

public interface ReactionManager {
    public Reaction addReaction(Reaction reaction);
    public boolean deleteReaction(Reaction reaction);
    public Reaction updateReaction(Reaction reaction);
    public List<Reaction> getAllReactions();
    public Reaction getReactionById(long id);
    public Reaction checkReaction(Creator creator, Video video);
    public boolean removeReactionVideoCreator(Creator creator, Video video);
}
