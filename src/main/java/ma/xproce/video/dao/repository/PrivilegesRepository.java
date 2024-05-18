package ma.xproce.video.dao.repository;

import ma.xproce.video.dao.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegesRepository extends JpaRepository<Privilege, Long> {
}
