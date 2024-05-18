package ma.xproce.video.dao.repository;

import ma.xproce.video.dao.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
}
