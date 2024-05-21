package ma.xproce.video.web;

import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.entity.FriendRequest;
import ma.xproce.video.service.CreatorManager;
import ma.xproce.video.service.FriendRequestManager;
import ma.xproce.video.service.dtos.CreatorDTO;
import ma.xproce.video.service.mapper.CreatorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FriendRequestController {
    @Autowired
    CreatorManager creatorManager;
    @Autowired
    FriendRequestManager friendRequestManager;
    @Autowired
    private CreatorMapper creatorMapper;

    @PostMapping("/api/user/friendSend")
    public ResponseEntity<String> friendRequest(@RequestBody Map<String, Object> payload){
        String senderUsername = payload.get("username").toString();
        String receiverUsername = payload.get("friend").toString();

        CreatorDTO senderDTO = creatorManager.findByUsername(senderUsername);
        CreatorDTO receiverDTO  = creatorManager.findByUsername(receiverUsername);

        Creator sender = creatorMapper.CreatorDTOToCreator(senderDTO);
        Creator receiver = creatorMapper.CreatorDTOToCreator(receiverDTO);

        FriendRequest friendRequest = friendRequestManager.getByReceiverSender(sender, receiver);
        if (friendRequest != null){
            friendRequestManager.cancelRequest(friendRequest);
            return ResponseEntity.ok("request cancelled");
        }
        friendRequestManager.sendRequest(senderDTO, receiverDTO);

        return ResponseEntity.ok("request sent");
    }



    @PostMapping("/api/user/friendAccept")
    public ResponseEntity<String> friendAccept(@RequestBody Map<String, Object> payload){
        String senderUsername = payload.get("username").toString();
        String receiverUsername = payload.get("friend").toString();

        CreatorDTO senderDTO = creatorManager.findByUsername(senderUsername);
        CreatorDTO receiverDTO  = creatorManager.findByUsername(receiverUsername);

        Creator sender = creatorMapper.CreatorDTOToCreator(senderDTO);
        Creator receiver = creatorMapper.CreatorDTOToCreator(receiverDTO);

        FriendRequest friendRequest = friendRequestManager.getByReceiverSender(sender, receiver);

        if (friendRequest != null){
            friendRequestManager.acceptFriendRequest(friendRequest);
            return ResponseEntity.ok("request accepted");
        }

        return ResponseEntity.ok("request not found");
    }

    @PostMapping("/api/user/friendReject")
    public ResponseEntity<String> friendReject(@RequestBody Map<String, Object> payload){
        String senderUsername = payload.get("username").toString();
        String receiverUsername = payload.get("friend").toString();

        CreatorDTO senderDTO = creatorManager.findByUsername(senderUsername);
        CreatorDTO receiverDTO  = creatorManager.findByUsername(receiverUsername);

        Creator sender = creatorMapper.CreatorDTOToCreator(senderDTO);
        Creator receiver = creatorMapper.CreatorDTOToCreator(receiverDTO);

        FriendRequest friendRequest = friendRequestManager.getByReceiverSender(sender, receiver);
        if (friendRequest != null){
            friendRequestManager.rejectFriendRequest(friendRequest);
            return ResponseEntity.ok("request accepted");
        }

        return ResponseEntity.ok("request not found");
    }

}
