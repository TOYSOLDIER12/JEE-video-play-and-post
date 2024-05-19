package ma.xproce.video.dao.repository;

import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.entity.Reaction;
import ma.xproce.video.dao.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    public List<Reaction> findByCreatorAndVideo(Creator creator, Video video);
}
