package ma.xproce.video.web;

import ma.xproce.video.service.ReactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ma.xproce.video.dao.entity.Reaction;

@RestController
public class ReactionController {
    @Autowired
    ReactionManager reactionManager;
    @PostMapping("/api/video/reactions")

    public String CreateReaction(@RequestBody Reaction reaction) {
        reactionManager.addReaction(reaction);
        return "nice";
    }



}
