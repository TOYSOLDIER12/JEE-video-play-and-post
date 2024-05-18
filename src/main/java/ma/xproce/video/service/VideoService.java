package ma.xproce.video.service;

import ma.xproce.video.dao.entity.Comment;
import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.entity.Reaction;
import ma.xproce.video.dao.entity.Video;
import ma.xproce.video.dao.repository.CreatorRepository;
import ma.xproce.video.dao.repository.VideoRepository;
import ma.xproce.video.service.dtos.CreatorDTO;
import ma.xproce.video.service.dtos.VideoDTO;
import ma.xproce.video.service.dtos.VideoDTOADD;
import ma.xproce.video.service.mapper.CreatorMapper;
import ma.xproce.video.service.mapper.HolyMapper;
import ma.xproce.video.service.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService implements VideoManager {
    @Autowired
    VideoRepository videoRepository;
    @Autowired
    CreatorMapper creatorMapper;
    @Autowired
    VideoMapper videoMapper;
    @Autowired
    HolyMapper holyMapper;
    @Autowired
    private CreatorRepository creatorRepository;

    @Override
    public VideoDTO addVideo(VideoDTOADD videoDTOADD) {
        if (videoDTOADD.getUrl().isEmpty()){
            System.out.println("no url was given");
            return null;
        }
        Creator creator = creatorRepository.findByUsername(videoDTOADD.getCreator().getUsername())
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        Video video = videoMapper.VideoDTOADDTOVideo(videoDTOADD);
        video.setCreator(creator);
        videoRepository.save(video);
        return videoMapper.VideoToVideoDTO(video);
    }

    @Override
    public VideoDTO updateVideo(VideoDTOADD videoDTOADD) {
        Video video = videoMapper.VideoDTOADDTOVideo(videoDTOADD);
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

        videoRepository.save(oldVideo);
        VideoDTO videoDTO = holyMapper.ToVideoDTO(oldVideo);
        return videoDTO;
    }

    @Override
    public boolean deleteVideo(long id) {
        Optional<Video> existingVideo = videoRepository.findById(id);
        if (existingVideo.isEmpty()) {
            System.out.println("No fookin' video found with that pissin' ID: " + id);
            return false;
        }

        videoRepository.delete(existingVideo.get());

        return !videoRepository.existsById(id);
    }


    @Override
    public List<VideoDTO> getAllVideos() {
        List<Video> videos = videoRepository.findAll();
        List<VideoDTO> videoDTOS = new ArrayList<>();
        for ( Video video : videos ) {
            VideoDTO videoDTO = holyMapper.ToVideoDTO(video);
            videoDTOS.add(videoDTO);
        }
        return videoDTOS;
    }

    @Override
    public VideoDTO getVideoById(long id) {
        if(!videoRepository.existsById(id)) {
            System.out.println("no creator was found matching that id" + id);
            return null;
        }
        Video video = videoRepository.getById(id);
        return holyMapper.ToVideoDTO(video);
    }
    @Override
    public List<VideoDTO> getVideoByCreator(CreatorDTO creatorDTO) {
            Creator creator = creatorMapper.CreatorDTOToCreator(creatorDTO);
            List<VideoDTO> videoDTOS = new ArrayList<>();
            for (Video video : creator.getVideos()){
                VideoDTO videoDTO = holyMapper.ToVideoDTO(video);
                videoDTOS.add(videoDTO);
            }
        return videoDTOS;
    }


}


