package ma.xproce.video.dao.repository;

import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    public List<FriendRequest> findFriendRequestBySenderAndReceiver(Creator sender, Creator receiver);
}
