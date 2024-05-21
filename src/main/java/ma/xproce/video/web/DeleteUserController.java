package ma.xproce.video.web;


import ma.xproce.video.service.CreatorManager;
import ma.xproce.video.service.VideoManager;
import ma.xproce.video.service.dtos.CreatorDTO;
import ma.xproce.video.service.dtos.CreatorDTOADD;
import ma.xproce.video.service.mapper.CreatorMapper;
import ma.xproce.video.service.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class DeleteUserController {

    @Autowired
    CreatorManager creatorManager;
    @Autowired
    CreatorMapper creatorMapper;
    @Autowired
    VideoManager videoManager;
    @Autowired
    VideoMapper videoMapper;

    @PostMapping("/api/admin/delete-user/")
    public ResponseEntity<String> delete(@RequestBody Map<String, Object> payload){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        CreatorDTO creatorViewer = creatorManager.findByUsername(authentication.getName());

        String username = payload.get("username").toString();

        CreatorDTO creatorDTO = creatorManager.findByUsername(username);
        System.out.println(creatorViewer.getUsername());
        if (creatorViewer == null || creatorDTO == null) {
            return ResponseEntity.ok("ara la carte");
        }

        // Proper string comparison
        boolean isAdmin = "admin".equals(creatorViewer.getRole().getName());

        if (!isAdmin) {
            return ResponseEntity.ok("gadarmi nta?");
        }
        CreatorDTOADD creatorDTOADD = creatorMapper.CreatorToCreatorDTOADD(creatorMapper.CreatorDTOToCreator(creatorDTO));
        creatorManager.deleteCeator(creatorDTOADD.getId());

        return ResponseEntity.ok("bar9iya wadi7a");
    }


    @PostMapping("/api/admin/delete-video/")
    public ResponseEntity<String> deleteVideo(@RequestBody Map<String, Object> payload){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        CreatorDTO creatorViewer = creatorManager.findByUsername(authentication.getName());



        if (creatorViewer == null) {
            return ResponseEntity.ok("ara la carte");
        }

        // Proper string comparison
        boolean isAdmin = "admin".equals(creatorViewer.getRole().getName());


        if (!isAdmin) {
            return ResponseEntity.ok("gadarmi nta?");
        }
        Long videoId = Long.valueOf(payload.get("videoId").toString());

         videoManager.deleteVideo(videoId);

        return ResponseEntity.ok("bar9iya wadi7a");
    }




}
