package ma.xproce.video.dao.repository;

import ma.xproce.video.dao.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment , Long> {
}
