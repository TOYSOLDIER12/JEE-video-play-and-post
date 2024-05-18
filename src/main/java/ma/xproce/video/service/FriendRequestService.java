package ma.xproce.video.service;

import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.entity.FriendRequest;
import ma.xproce.video.dao.enumerations.Stat;
import ma.xproce.video.dao.repository.CreatorRepository;
import ma.xproce.video.dao.repository.FriendRequestRepository;
import ma.xproce.video.service.dtos.CreatorDTO;
import ma.xproce.video.service.dtos.CreatorDTOADD;
import ma.xproce.video.service.mapper.CreatorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendRequestService implements FriendRequestManager{
    @Autowired
    FriendRequestRepository friendRequestRepository;
    @Autowired
    CreatorManager creatorManager;
    @Autowired
    CreatorMapper creatorMapper;

    @Override
    public void acceptFriendRequest(FriendRequest friendRequest) {
        friendRequest.setStatus(Stat.ACCEPTED);
        friendRequest.getReceiver().addFriend(friendRequest.getSender());
        friendRequest.getSender().addFriend(friendRequest.getReceiver());
        CreatorDTOADD receiver = creatorMapper.CreatorToCreatorDTOADD(friendRequest.getReceiver());
        CreatorDTOADD sender = creatorMapper.CreatorToCreatorDTOADD(friendRequest.getSender());
        creatorManager.addCreator(receiver);
        creatorManager.addCreator(sender);
        friendRequestRepository.save(friendRequest);

    }
    @Override
    public void rejectFriendRequest(FriendRequest friendRequest){
        friendRequest.setStatus(Stat.REJECTED);
        friendRequestRepository.delete(friendRequest);
    }
    @Override
    public void sendRequest(long sendId, long freundId){
        CreatorDTO sender = creatorManager.getById(sendId);
        CreatorDTO freund = creatorManager.getById(freundId);
        if(sender != null && freund != null) {
            FriendRequest friendRequest = new FriendRequest();

            friendRequest.setReceiver(creatorMapper.CreatorDTOToCreator(sender));
            friendRequest.setReceiver(creatorMapper.CreatorDTOToCreator(freund));
            friendRequest.setStatus(Stat.PENDING);
            creatorMapper.CreatorDTOToCreator(freund).addFriendRequest(friendRequest);
        }
    }
}

