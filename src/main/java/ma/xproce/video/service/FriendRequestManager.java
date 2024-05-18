package ma.xproce.video.service;


import ma.xproce.video.dao.entity.FriendRequest;

public interface FriendRequestManager {
    public void acceptFriendRequest(FriendRequest friendRequest);
    public void rejectFriendRequest(FriendRequest friendRequest);
    public void sendRequest(long sendId, long freundId);
}
