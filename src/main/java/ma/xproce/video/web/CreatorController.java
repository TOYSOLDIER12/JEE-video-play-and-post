package ma.xproce.video.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.service.CreatorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class CreatorController {
    @Value("${upload-dir}")
    private String uploadDir;
    @Autowired
    CreatorManager creatorManager;


    @GetMapping("/login")
        public String login() {return "login";}

    @PostMapping("/login")
        public String login(Model model, @RequestParam(name = "username") String username, @RequestParam(name = "password") String password, HttpServletRequest request) {
        boolean authenticated = creatorManager.checkLogin(username, password);
        if (authenticated) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("loggedIn", true);
            return "redirect:/index";
        } else {
            // Handle failed login attempt
            String message = "chkon nta ??";
            model.addAttribute("message", message);
            return "/login";
        }
    }
    @GetMapping("/sign")
        public String sign() {
            return "signup";
            }
    @PostMapping("/sign")
        public String sign(@RequestParam(name = "mail") String mail, @RequestParam(name = "username") String username, @RequestParam(name = "password") String password, @RequestParam(name = "profile") MultipartFile  profile) throws IOException {
        if(profile.isEmpty()) {
            System.out.println("wtf is this bogus");
            return "rediret:post?error";
        }
        byte[] bytes = profile.getBytes();
        String originalFilename = profile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + extension;
        Path uploadPath = Paths.get(uploadDir, uniqueFilename);
        Files.write(uploadPath, bytes);
        Creator creator = new Creator();
        creator.setUsername(username);
        creator.setPassword(password);
        creator.setProfile("/"+uniqueFilename);
        creator.setMail(mail);
        creatorManager.addCreator(creator);
        return "redirect:/";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:login";
    }


    }
