package ma.xproce.video.service;


import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.entity.FriendRequest;
import ma.xproce.video.service.dtos.CreatorDTO;

import java.util.List;

public interface FriendRequestManager {
    public void acceptFriendRequest(FriendRequest friendRequest);
    public void rejectFriendRequest(FriendRequest friendRequest);
    public void sendRequest(CreatorDTO sender, CreatorDTO freund);
    public FriendRequest getByReceiverSender(Creator sender, Creator receiver);
    public boolean cancelRequest(long sendId, long freundId);
    public boolean cancelRequest(FriendRequest friendRequest);
    public List<FriendRequest> getCreatorRequest(CreatorDTO creatorDTO);
    public List<FriendRequest> getFriendRequestsByReceiver(CreatorDTO receiver);
}
