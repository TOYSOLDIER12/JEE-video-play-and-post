package ma.xproce.video.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.entity.Video;
import ma.xproce.video.service.CreatorManager;
import ma.xproce.video.service.VideoManager;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class VideoController {
    @Value("${upload-dir}")
    private String uploadDir;

    @Autowired
    private VideoManager videoManager;

    @Autowired
    private CreatorManager creatorManager;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedIn") != null && (boolean) session.getAttribute("loggedIn")){
            System.out.println("logged "+ session.getAttribute("username"));
            return "redirect:/index";
        } else {
            return "redirect:/login";
        }
    }
    @GetMapping("/index")
    public String index(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            String username = (String) session.getAttribute("username");
            Optional<Creator> optionalCreator = creatorManager.findByUsername(username);
            if (optionalCreator.isPresent()) {
                Creator creator = optionalCreator.get();
                Optional<List<Video>> optionalVideos = videoManager.getVideoByCreator(creator);
                List<Video> videos = optionalVideos.orElse(Collections.emptyList());
                model.addAttribute("videos", videos);
            } else {
                System.out.println("who are you again? what u doing here? go back to sign");
                return "redirect:/login";
            }
        }
        else {
                System.out.println("not logged in mate, go back to login");
                return "redirect:/login";
            }

        return "index";
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
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            String username = (String) session.getAttribute("username");
            Optional<Creator> optionalCreator = creatorManager.findByUsername(username);

            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDir + file.getOriginalFilename());
            Files.write(path, bytes);
            String filePath = path.toString();

            if (optionalCreator.isPresent()) {
                Creator creator = optionalCreator.get();
                Video video = new Video();
                video.setName(name);
                video.setUrl(file.getOriginalFilename());
                video.setCreator(creator);
                videoManager.addVideo(video);
            }
        }
        return "redirect:/index";
    }


}