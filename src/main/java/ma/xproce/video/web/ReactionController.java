package ma.xproce.video.web;

import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.entity.Video;
import ma.xproce.video.dao.enumerations.Type;
import ma.xproce.video.service.CreatorManager;

import ma.xproce.video.service.Exception.ReactionExistsException;
import ma.xproce.video.service.ReactionManager;
import ma.xproce.video.service.VideoManager;
import ma.xproce.video.service.dtos.CreatorDTO;
import ma.xproce.video.service.dtos.VideoDTO;
import ma.xproce.video.service.mapper.CreatorMapper;
import ma.xproce.video.service.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;
import ma.xproce.video.dao.entity.Reaction;

import java.util.Map;

import static java.lang.Enum.valueOf;

@RestController
public class ReactionController {
    @Autowired
    ReactionManager reactionManager;
    @Autowired
    private CreatorMapper creatorMapper;
    @Autowired
    private CreatorManager creatorManager;
    @Autowired
    private VideoManager videoManager;
    @Autowired
    private VideoMapper videoMapper;

    @PostMapping("/api/video/reactions")

    public ResponseEntity<String> CreateReaction(@RequestBody Map<String, Object> payload) {

        Long videoId = Long.valueOf(payload.get("videoId").toString());
        String reaction = payload.get("reaction").toString();
        String username = payload.get("username").toString();

        CreatorDTO creatorDTO = creatorManager.findByUsername(username);
        Creator creator = creatorMapper.CreatorDTOToCreator(creatorDTO);
        VideoDTO videoDTO = videoManager.getVideoById(videoId);
        Video video = videoMapper.VideoDTOTOVideo(videoDTO);

        Reaction reaction1 = reactionManager.checkReaction(creator, video);



        if (reaction1 != null) {

            if (reaction1.getType().name() == reaction) {
                reactionManager.removeReactionVideoCreator(creator, video);
                return ResponseEntity.ok("Reaction removed");
            }
            reactionManager.removeReactionVideoCreator(creator, video);
        }


        Reaction reactionToSave = new Reaction(1, (Type) valueOf(Type.class, reaction), video, creator);
        reactionManager.addReaction(reactionToSave);

        return ResponseEntity.ok("Reaction added");

    }



}
