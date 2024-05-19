package ma.xproce.video.service;

import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.entity.Reaction;
import ma.xproce.video.dao.entity.Video;
import ma.xproce.video.dao.repository.CreatorRepository;
import ma.xproce.video.dao.repository.ReactionRepository;
import ma.xproce.video.dao.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReactionService implements ReactionManager{
@Autowired
 ReactionRepository reactionRepository;

@Autowired
    VideoRepository videoRepository;

    @Override
    public Reaction addReaction(Reaction reaction) {
        if (reaction.getCreator() == null || reaction.getVideo() == null )
            return null;
        return reactionRepository.save(reaction);
    }

    @Override
    public boolean deleteReaction(Reaction reaction) {
        Optional<Reaction> reaction1 = reactionRepository.findById(reaction.getId());
        if (reaction1.isEmpty())
            return false;
        reactionRepository.delete(reaction1.get());
        return !reactionRepository.existsById(reaction.getId());
    }

    @Override
    public Reaction updateReaction(Reaction reaction) {
        Optional<Reaction> reaction1 = reactionRepository.findById(reaction.getId());
        if (reaction1.isEmpty())
            return null;
        Reaction newreaction = reaction1.get();
        newreaction.setCreator(reaction.getCreator());
        newreaction.setVideo(reaction.getVideo());
        newreaction.setType(reaction.getType());
        return reactionRepository.save(newreaction);

    }

    @Override
    public List<Reaction> getAllReactions() {
        return reactionRepository.findAll();
    }

    @Override
    public Reaction getReactionById(long id) {
        Optional<Reaction> reaction1 = reactionRepository.findById(id);
        return reaction1.orElse(null);
    }

    @Override
    public Reaction checkReaction(Creator creator, Video video) {
        List<Reaction> reactions = reactionRepository.findByCreatorAndVideo(creator, video);

        if (reactions.isEmpty())
            return null;


        return reactions.get(0);
    }


    @Override
    public boolean removeReactionVideoCreator(Creator creator, Video video) {

        List<Reaction> reactions = reactionRepository.findByCreatorAndVideo(creator, video);
        reactionRepository.delete(reactions.get(0));
        if(reactionRepository.findByCreatorAndVideo(creator,video) !=null)
            return false;
        return true;
    }


}
