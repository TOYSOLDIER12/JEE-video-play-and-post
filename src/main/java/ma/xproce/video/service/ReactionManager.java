package ma.xproce.video.service;

import ma.xproce.video.dao.entity.Reaction;

import java.util.List;

public interface ReactionManager {
    public Reaction addReaction(Reaction reaction);
    public boolean deleteReaction(Reaction reaction);
    public Reaction updateReaction(Reaction reaction);
    public List<Reaction> getAllReactions();
    public Reaction getReactionById(long id);
}
