package ma.xproce.video.service;

import ma.xproce.video.dao.entity.Creator;

import java.util.List;
import java.util.Optional;

public interface CreatorManager {
    public Creator addCreator(Creator creator);
    public Creator updateCreator(Creator creator);
    public boolean deleteCeator(Creator creator);
    public List<Creator> getAllCreators();
    public Creator getById(long id);

    public boolean checkLogin(String username, String password);
    public Optional<Creator> findByUsername(String username);
}
