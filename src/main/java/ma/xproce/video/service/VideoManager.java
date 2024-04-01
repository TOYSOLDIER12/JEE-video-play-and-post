package ma.xproce.video.service;

import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.entity.Video;

import java.util.List;
import java.util.Optional;

public interface VideoManager {
    public Video addVideo(Video video);
    public Video updateVideo(Video video);
    public boolean deleteVideo(Video video);
    public List<Video> getAllVideos();
    public Video getVideoById(long id);


    public Optional<List<Video>> getVideoByCreator(Creator creator);
}
