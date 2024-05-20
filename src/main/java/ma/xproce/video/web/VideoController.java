package ma.xproce.video.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ma.xproce.video.dao.entity.*;
import ma.xproce.video.dao.enumerations.Type;
import ma.xproce.video.service.*;
import ma.xproce.video.service.dtos.*;
import ma.xproce.video.service.mapper.CreatorMapper;
import ma.xproce.video.service.mapper.ReactionMiniMapper;
import ma.xproce.video.service.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.SecurityRequestMatchersManagementContextConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class VideoController {
    @Value("${upload-dir}")
    private String uploadDir;

    @Autowired
    private VideoManager videoManager;

    @Autowired
    private CreatorManager creatorManager;
    @Autowired
    CreatorMapper creatorMapper;
    @Autowired
    VideoMapper videoMapper;
    @Autowired
    private CommentManager commentManager;
    @Autowired
    FriendRequestManager friendRequestManager;

    @GetMapping("/")
    public String index(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()){
            session.setAttribute("username", authentication.getName());
            session.setAttribute("loggedIn", true);
            return "redirect:/index";
        } else {
            return "redirect:/login";
        }
    }
    @GetMapping("/my-videos/{username}")
    public String videoIndex(Model model, @PathVariable String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()){
            CreatorDTO creator = creatorManager.findByUsername(username);
            if (creator !=null) {
                model.addAttribute("creator", creator);
                List<VideoDTO> optionalVideos = creator.getVideosDTO();
                Map<Type, String> iconClasses = ReactionMiniMapper.getIconClasses();
                model.addAttribute("iconClasses", iconClasses);
                model.addAttribute("videos", optionalVideos);
            } else {
                System.out.println("who are you again? what u doing here? go back to sign");
                return "redirect:/login";
            }
        }
        else {
                System.out.println("not logged in mate, go back to login");
                return "redirect:/login";
            }

        return "my-videos";
    }
    @GetMapping("/index")
    public String index(Model model, @RequestParam(name = "page", defaultValue = "0" ) int page, @RequestParam(name = "taille", defaultValue = "5" ) int taille, @RequestParam(name = "search", defaultValue = "") String keyword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()){
                String username = authentication.getName();
                CreatorDTO creator = creatorManager.findByUsername(username);
                if (creator == null) {
                    return "redirect:/login";
                }



                List< FriendRequest> friendRequests = friendRequestManager.getCreatorRequest(creator);
                model.addAttribute("friendRequests", friendRequests);
                model.addAttribute("username", creator.getUsername());
                Page<VideoDTO> videos = videoManager.searchVideo(keyword, page, taille);
                Map<Type, String> iconClasses = ReactionMiniMapper.getIconClasses();



                Map<Long, Map<String, Integer>> videoReactions = new HashMap<>();
                for (VideoDTO video : videos) {
                    Map<String, Integer> reactionCounts = new HashMap<>();
                    for (Type type : Type.values()) {
                        reactionCounts.put(type.name(), getReactionCount(video.getReactions(), type));
                    }
                    videoReactions.put(video.getId(), reactionCounts);
                }


                int[] pages = new int[videos.getTotalPages()];
                for (int i = 0; i < pages.length; i++) {
                    pages[i] = i;
                }
                model.addAttribute("pages", pages);
                model.addAttribute("currentPage", page);
                model.addAttribute("keyword", keyword);


                model.addAttribute("videoReactions", videoReactions);
                model.addAttribute("iconClasses", iconClasses);
                model.addAttribute("videos", videos.getContent());

                if (creator.getRole().getName().equals("admin"))
                    return "/admin";


        }
        else {
            System.out.println("not logged in mate, go back to login");
            return "redirect:/login";
        }

        return "index";
    }
    public int getReactionCount(List<Reaction> reactions, Type type) {
        return (int) reactions.stream().filter(r -> r.getType() == type).count();
    }

    @GetMapping("/video={id}/comments")
    public String getComment(@PathVariable(name = "id")long id, Model model){
        VideoDTO videoDTO = videoManager.getVideoById(id);
        if (videoDTO == null){
            return "redirect:/index";
        }
        model.addAttribute("video", videoDTO);
        return "comment";
    }
    @PostMapping("/video={id}/comments")
    public String postComment(@PathVariable(name = "id")long id,String commentContenu) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {

            VideoDTO videoDTO = videoManager.getVideoById(id);
            if (videoDTO == null) {
                return "redirect:/index";
            }
            CreatorDTO creatorDTO = creatorManager.findByUsername(authentication.getName());
            Creator creator = creatorMapper.CreatorDTOToCreator(creatorDTO);
            Video video = videoMapper.VideoDTOTOVideo(videoDTO);

            Comment comment = new Comment();
            comment.setContent(commentContenu);
            comment.setDate(LocalDateTime.now());

            creatorDTO = creatorManager.updateCreator(creatorMapper.CreatorToCreatorDTOADD(creator));
            creator = creatorMapper.CreatorDTOToCreator(creatorDTO);

            comment.setCreator(creator);
            comment.setVideo(video);
            creator.addComment(comment);


            System.out.println(creator.getId());
            commentManager.addComment(comment);

            video.addComment(comment);


            return "redirect:/video="+id+"/comments";
        }
        return "redirect:/index";
    }


    @GetMapping("/deleteVideo")
    public String deleteVideo(@RequestParam(name = "id")long id, HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        VideoDTO video = videoManager.getVideoById(id);
        if (authentication.isAuthenticated()){
            CreatorDTO currentUser = creatorManager.findByUsername(authentication.getName());
            if (Objects.equals(video.getCreatorDTO().getUsername(), currentUser.getUsername()) || currentUser.getRole().getName().equals("admin")) {
                videoManager.deleteVideo(id);
                return "/index";
            }
        }
        else
            System.out.println("no can do");
            return "/index";

    }
    @GetMapping("/updateVideo")
    public String updateVideo(@RequestParam(name = "id")long id,@RequestParam(name = "name")String name,@RequestParam(name = "description")String description, HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        VideoDTO videoDTO = videoManager.getVideoById(id);
        if (authentication.isAuthenticated()){
            CreatorDTO currentUser = creatorManager.findByUsername(authentication.getName());
            if (videoDTO.getCreatorDTO().getUsername() == currentUser.getUsername() || currentUser.getRole().getName().equals("admin")) {
                VideoDTOADD videoDTOADD = videoMapper.VideoToVideoDTOADD(videoMapper.VideoDTOTOVideo(videoDTO));
                videoDTOADD.setName(name);
                videoDTOADD.setDescription(description);
                videoManager.updateVideo(videoDTOADD);
                return "/index";
            }
        }
        else
            System.out.println("no can do");
        return "/index";

    }

    @GetMapping("/post")
    public String post() {
        return "post";
    }

    @PostMapping("/post")
    public String post(@RequestParam(name = "name") String name,
                       @RequestParam(name = "file") MultipartFile file,
                       HttpServletRequest request) throws IOException {
        if(file.isEmpty()){
            System.out.println("wtf is this bogus");
            return "rediret:post?error";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            String username = authentication.getName();
            CreatorDTO creator = creatorManager.findByUsername(username);

            byte[] bytes = file.getBytes();
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFilename = UUID.randomUUID().toString() + extension;
            Path uploadPath = Paths.get(uploadDir, uniqueFilename);
            Files.write(uploadPath, bytes);

            if (creator != null) {
                VideoDTOADD video = new VideoDTOADD();
                video.setName(name);    
                video.setUrl("/"+uniqueFilename);
                Creator creator1 = creatorMapper.CreatorDTOToCreator(creator);
                CreatorDTOADD creatorDTOADD = creatorMapper.CreatorToCreatorDTOADD(creator1);
                video.setCreator(creator1);

                videoManager.addVideo(video);
            }
        }
        return "redirect:/index";
    }



}