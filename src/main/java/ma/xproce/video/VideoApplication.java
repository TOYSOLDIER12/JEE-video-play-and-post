package ma.xproce.video;

import ma.xproce.video.dao.entity.Creator;

import ma.xproce.video.dao.entity.Privilege;
import ma.xproce.video.dao.entity.Role;
import ma.xproce.video.dao.entity.Video;
import ma.xproce.video.dao.repository.PrivilegesRepository;
import ma.xproce.video.dao.repository.RoleRepository;
import ma.xproce.video.service.*;

import ma.xproce.video.service.dtos.CreatorDTO;
import ma.xproce.video.service.dtos.CreatorDTOADD;
import ma.xproce.video.service.mapper.CreatorMapper;
import ma.xproce.video.service.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Date;



@SpringBootApplication
public class VideoApplication implements CommandLineRunner {


    @Autowired
    CreatorManager creatorManager;
    @Autowired
    VideoManager videoManager;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CreatorMapper creatorMapper;
    @Autowired
    VideoMapper videoMapper;
    @Autowired
    private CreatorService creatorService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PrivilegesRepository privilegesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(VideoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

      /*  Creator creator = new Creator();
        creator.setUsername("sidna");
        creator.setPassword("1234");
        creator.setMail("mail");
        creator.setProfile("/image.jpeg");
        Role role = new Role();
        role.setName("admin");
        roleRepository.save(role);
        creator.setRole(role);
        creatorManager.addCreator(creatorMapper.CreatorToCreatorDTOADD(creator));

        Video video = new Video();
        video.setName("last video");
        video.setDescription("kykhla3");
        video.setUrl("/safir.mp4");
        video.setDatePublication(new Date(1999,12,01));
        videoManager.addVideo(videoMapper.VideoToVideoDTOADD(video));
        System.out.println(creator.getUsername());
        video.setCreator(creator);



        creator.addVideo(video);
        videoManager.updateVideo(videoMapper.VideoToVideoDTOADD(video));
        creatorManager.addCreator(creatorMapper.CreatorToCreatorDTOADD(creator));


        Privilege remove = new Privilege(1,"remove",null);
        privilegesRepository.save(remove);
        Role user = new Role();
        user.setName("user");
        Role admin = new Role();
        admin.setName("admin");
        admin.addPrivilege(remove);
        roleRepository.save(admin);
        CreatorDTOADD creator = new CreatorDTOADD();
        creator.setRole(admin);
        creator.setMail("admin@mail.com");
        creator.setPassword(passwordEncoder.encode( "password"));
        creator.setUsername("admin");
        creator.setProfile("/admin.jpeg");
        creatorService.addCreator(creator);
        admin.addCreator(creatorMapper.CreatorDTOADDToCreator(creator));
        roleRepository.save(user);

*/


    }
}
