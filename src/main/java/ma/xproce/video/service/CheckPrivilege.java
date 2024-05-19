package ma.xproce.video.service;

import ma.xproce.video.service.dtos.CreatorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CheckPrivilege {
    @Autowired
    CreatorManager creatorManager;

    public boolean checkPrivilege(Authentication authentication , String username){

        CreatorDTO creatorViewer = creatorManager.findByUsername(authentication.getName());
        CreatorDTO creatorDTO = creatorManager.findByUsername(username);

        if (creatorViewer == null || creatorDTO == null) {
            return false;
        }

        // Proper string comparison
        boolean isAdmin = "admin".equals(creatorViewer.getRole().getName());
        boolean isSameUser = authentication.getName().equals(creatorDTO.getUsername());

        if (!isSameUser && !isAdmin) {
            return false;
        }


        return true;
    }

}
