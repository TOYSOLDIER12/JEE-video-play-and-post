package ma.xproce.video.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.service.CreatorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ma.xproce.video.dao.entity.Video;
import ma.xproce.video.service.VideoManager;

import javax.net.ssl.HandshakeCompletedEvent;
import java.util.List;
@Controller
public class CreatorController {
    @Autowired
    CreatorManager creatorManager;


    @GetMapping("/login")
        public String login(Model model) {return "login";}

    @PostMapping("/login")
        public String login(Model model, @RequestParam(name = "username") String username, @RequestParam(name = "password") String password, HttpServletRequest request) {
        boolean authenticated = creatorManager.checkLogin(username, password);
        if (authenticated) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("loggedIn", true);
            return "redirect:/";
        } else {
            // Handle failed login attempt
            String message = "chkon nta ??";
            model.addAttribute("message", message);
            return "redirect:/login?error";
        }
    }
    @GetMapping("/sign")
        public String sign(Model model) {
            return "signup";
            }
    @PostMapping("/sign")
        public String sign(Model model, @RequestParam(name = "mail") String mail, @RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
            Creator creator = new Creator();
            creator.setUsername(username);
            creator.setPassword(password);
            creator.setMail(mail);
            creatorManager.addCreator(creator);
            return "redirect:/";
        }
    }
