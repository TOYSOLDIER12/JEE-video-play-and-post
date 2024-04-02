package ma.xproce.video;

import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.entity.Video;
import ma.xproce.video.service.CreatorManager;
import ma.xproce.video.service.VideoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@SpringBootApplication
public class VideoApplication implements CommandLineRunner {
   /* @Autowired
    VideoRepository videoRepository;
    @Autowired
    CreatorRepository creatorRepository;
    */
    @Autowired
    CreatorManager creatorManager;
    @Autowired
    VideoManager videoManager;
    public static void main(String[] args) {
        SpringApplication.run(VideoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // repository test
       /* Collection<Video> videos = new ArrayList<>();
        Video video = new Video();
        video.setName("last video");
        video.setDescription("kykhla3");
        video.setUrl("/home/toy/video.mp4");
        video.setDatePublication(new Date(1999,12,01));
        videos.add(video);
        videoRepository.save(video);
        Creator creator = new Creator();
        creator.setVideos(videos);
        creator.setName("ghir ana");
        creator.setMail("mail");
        creatorRepository.save(creator);
        video.setCreator(creator);
        videoRepository.save(video);
*/
        // servide test
        Collection<Video> videos = new ArrayList<>();
        Video video = new Video();
        video.setName("last video");
        video.setDescription("kykhla3");
        video.setUrl("/home/toy/video.mp4");
        video.setDatePublication(new Date(1999,12,01));
        videos.add(video);
        Video video2 = new Video();
        video2.setName("real last video");
        video2.setDescription("g3ma kykhla3");
        video2.setUrl("../resources/static/video.mp4");
        video2.setDatePublication(new Date(1998,12,01));
        videos.add(video2);
        videoManager.addVideo(video);
        videoManager.addVideo(video2);
        Creator creator = new Creator();
        creator.setVideos(videos);
        creator.setUsername("ghir ana");
        creator.setPassword("1234");
        creator.setMail("mail");
        creator.setProfile("/image.jpeg");
        creatorManager.addCreator(creator);
        video.setCreator(creator);
        videoManager.addVideo(video);
        video.setUrl("/safir.mp4");
        videoManager.updateVideo(video);
        creator.setUsername("sidna");
        creatorManager.updateCreator(creator);
        videoManager.deleteVideo(videoManager.getVideoById(2));
        creatorManager.checkLogin("sidna","1234" );

    }
}
