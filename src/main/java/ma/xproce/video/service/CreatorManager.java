package ma.xproce.video.service;

import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.service.dtos.CreatorDTO;
import ma.xproce.video.service.dtos.CreatorDTOADD;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface CreatorManager {
    public CreatorDTO addCreator(CreatorDTOADD creatorDTOADD);
    public CreatorDTO updateCreator(CreatorDTOADD creatorDTOADD);
    public boolean deleteCeator(long id);
    public List<CreatorDTO> getAllCreators();
    public CreatorDTO getById(long id);

    public boolean checkLogin(String username, String password);
    public CreatorDTO findByUsername(String username);
    public boolean checkPrivilege(Authentication authentication , String username);
}
