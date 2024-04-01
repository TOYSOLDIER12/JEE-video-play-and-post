package ma.xproce.video.dao.repository;

import ma.xproce.video.dao.entity.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreatorRepository extends JpaRepository<Creator, Long> {
    public Optional<Creator> findByUsername(String username);
}
