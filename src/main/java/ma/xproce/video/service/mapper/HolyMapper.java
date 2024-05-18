package ma.xproce.video.service.mapper;

import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.entity.Video;
import ma.xproce.video.service.dtos.CreatorDTO;
import ma.xproce.video.service.dtos.CreatorDTOADD;
import ma.xproce.video.service.dtos.VideoDTO;
import ma.xproce.video.service.dtos.VideoDTOADD;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HolyMapper {
    ModelMapper holyMapper = new ModelMapper();
    @Autowired
    CreatorMapper creatorMapper;
    @Autowired
    VideoMapper videoMapper;
    public CreatorDTO ToCreatorDTO(Creator creator) {
        CreatorDTO creatorDTO = creatorMapper.CreatorToCreatorDTO(creator);
        List<VideoDTO> videoDTOList = new ArrayList<>();
        for (Video video : creator.getVideos()) {
            VideoDTO videoDTO = videoMapper.VideoToVideoDTO(video);
            videoDTOList.add(videoDTO);
        }
        creatorDTO.setVideosDTO(videoDTOList);
        return creatorDTO;

    }
    public VideoDTO ToVideoDTO(Video video) {
        VideoDTO videoDTO = videoMapper.VideoToVideoDTO(video);
        CreatorDTO creatorDTO = creatorMapper.CreatorToCreatorDTO(video.getCreator());
        videoDTO.setCreatorDTO(creatorDTO);
        return  videoDTO;
    }
    public Creator CreatorDTOADDToCreator(CreatorDTOADD creatorDTOADD) {
        Creator creator = creatorMapper.CreatorDTOADDToCreator(creatorDTOADD);
        List<Video> videoList = new ArrayList<>();
        for (VideoDTOADD videoDTOADD : creatorDTOADD.getVideos()) {
            Video video = videoMapper.VideoDTOADDTOVideo(videoDTOADD);
            videoList.add(video);
        }
        creator.setVideos(videoList);
        return creator;

    }
    public Video VideoDTOADDToVideo(VideoDTOADD videoDTOADD) {
        Video video = videoMapper.VideoDTOADDTOVideo(videoDTOADD);
        Creator creator = videoDTOADD.getCreator();
        video.setCreator(creator);
        return  video;
    }
}
