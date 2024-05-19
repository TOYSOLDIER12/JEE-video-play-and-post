package ma.xproce.video.service;

import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.entity.FriendRequest;
import ma.xproce.video.dao.enumerations.Stat;

import ma.xproce.video.dao.repository.FriendRequestRepository;
import ma.xproce.video.service.dtos.CreatorDTO;
import ma.xproce.video.service.dtos.CreatorDTOADD;
import ma.xproce.video.service.mapper.CreatorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void sendRequest(CreatorDTO sender, CreatorDTO freund){
        if(sender != null && freund != null) {
            FriendRequest friendRequest = new FriendRequest();

            friendRequest.setSender(creatorMapper.CreatorDTOToCreator(sender));
            friendRequest.setReceiver(creatorMapper.CreatorDTOToCreator(freund));
            friendRequest.setStatus(Stat.PENDING);
            creatorMapper.CreatorDTOToCreator(freund).addFriendRequest(friendRequest);
            friendRequestRepository.save(friendRequest);
        }
    }

    @Override
    public FriendRequest getByReceiverSender(Creator sender, Creator receiver) {

        List<FriendRequest> friendRequests = friendRequestRepository.findFriendRequestBySenderAndReceiver(sender, receiver);

        if(friendRequests.isEmpty())
            return null;

        return friendRequests.get(0);
    }

    @Override
    public boolean cancelRequest(long sendId, long freundId) {
        Creator sender = creatorMapper.CreatorDTOToCreator(creatorManager.getById(sendId));
        Creator freund = creatorMapper.CreatorDTOToCreator(creatorManager.getById(freundId));

        FriendRequest friendRequest = friendRequestRepository.findFriendRequestBySenderAndReceiver(sender, freund).get(0);
        friendRequestRepository.delete(friendRequest);
        return !friendRequestRepository.existsById(friendRequest.getId());
    }

    @Override
    public boolean cancelRequest(FriendRequest friendRequest) {
        friendRequestRepository.delete(friendRequest);
        return !friendRequestRepository.existsById(friendRequest.getId());
    }
    @Override
    public List<FriendRequest> getCreatorRequest(CreatorDTO creatorDTO){

        return friendRequestRepository.findFriendRequestByReceiver(creatorMapper.CreatorDTOToCreator(creatorDTO));

    }

}

