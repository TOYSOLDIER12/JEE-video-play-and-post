package ma.xproce.video.dao.repository;

import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.entity.Video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    Optional<List<Video>> findVideosByCreator(Creator creator);
    Page<Video> findByNameContains(String keyword, Pageable pageable);
}
