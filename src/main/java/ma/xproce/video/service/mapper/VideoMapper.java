package ma.xproce.video.service.mapper;

import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.entity.Video;
import ma.xproce.video.service.dtos.CreatorDTO;
import ma.xproce.video.service.dtos.CreatorDTOADD;
import ma.xproce.video.service.dtos.VideoDTO;
import ma.xproce.video.service.dtos.VideoDTOADD;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {
    ModelMapper videoMapper = new ModelMapper();
    public VideoDTO VideoToVideoDTO(Video video) {
        return videoMapper.map(video, VideoDTO.class);
    }
    public Video VideoDTOTOVideo(VideoDTO videoDTO){
        return videoMapper.map(videoDTO, Video.class);
    }
    public Video VideoDTOADDTOVideo(VideoDTOADD videoDTOADD){
        return videoMapper.map(videoDTOADD, Video.class);
    }
    public VideoDTOADD VideoToVideoDTOADD(Video video){
        return videoMapper.map(video, VideoDTOADD.class);
    }

}
