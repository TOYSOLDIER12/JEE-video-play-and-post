package ma.xproce.video.service;

import ma.xproce.video.dao.entity.Comment;
import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.entity.Reaction;
import ma.xproce.video.dao.entity.Video;
import ma.xproce.video.service.dtos.CreatorDTO;
import ma.xproce.video.service.dtos.VideoDTO;
import ma.xproce.video.service.dtos.VideoDTOADD;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface VideoManager {
    public VideoDTO addVideo(VideoDTOADD videoDTOADD);

    public VideoDTO updateVideo(VideoDTOADD videoDTOADD);

    public boolean deleteVideo(long id);

    public List<VideoDTO> getAllVideos();

    public VideoDTO getVideoById(long id);

    public List<VideoDTO> getVideoByCreator(CreatorDTO creatorDTO);
    public Page<VideoDTO> searchVideo(String keyword, int page, int taille);
}