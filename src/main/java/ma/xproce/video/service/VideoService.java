package ma.xproce.video.service;

import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.entity.Video;
import ma.xproce.video.dao.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoService implements VideoManager {
@Autowired
    VideoRepository videoRepository;
    @Override
    public Video addVideo(Video video) {
        if (video.getUrl().isEmpty()){
            System.out.println("no url was given");
            return null;
        }
        return videoRepository.save(video);
    }

    @Override
    public Video updateVideo(Video video) {
        Optional<Video> existingVideo = videoRepository.findById(video.getId());

        if(existingVideo.isEmpty()) {
            System.out.println("There ain't no bloody Video with this bleedin' id: " + video.getId());
            return null;
        }

        Video oldVideo = existingVideo.get();
        oldVideo.setDatePublication(video.getDatePublication());
        oldVideo.setName(video.getName());
        oldVideo.setCreator(video.getCreator());
        oldVideo.setDescription(video.getDescription());
        oldVideo.setUrl(video.getUrl());

        return videoRepository.save(oldVideo);

    }

    @Override
    public boolean deleteVideo(Video video) {
        Optional<Video> existingVideo = videoRepository.findById(video.getId());
        if (existingVideo.isEmpty()) {
            System.out.println("No fookin' video found with that pissin' ID: " + video.getId());
            return false;
        }

        videoRepository.delete(existingVideo.get());

        return !videoRepository.existsById(video.getId());
    }


    @Override
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    @Override
    public Video getVideoById(long id) {
        if(videoRepository.getById(id) == null) {
            System.out.println("no creator was found matching that id" + id);
            return null;
        }
        return videoRepository.getById(id);
    }
    @Override
    public Optional<List<Video>> getVideoByCreator(Creator creator) {
            return videoRepository.findVideosByCreator(creator);
        }
    }


