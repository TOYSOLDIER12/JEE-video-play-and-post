package ma.xproce.video.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.entity.Role;

import ma.xproce.video.service.CreatorManager;
import ma.xproce.video.service.RoleService;
import ma.xproce.video.service.dtos.CreatorDTO;
import ma.xproce.video.service.dtos.CreatorDTOADD;
import ma.xproce.video.service.mapper.CreatorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    private RoleService roleService;
    @Autowired
    private CreatorMapper creatorMapper;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(Model model, @RequestParam(name = "username") String username, @RequestParam(name = "password") String password, HttpServletRequest request, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            session.setAttribute("username", username);
            session.setAttribute("loggedIn", true);
            return "redirect:/index";
        } else {
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
    public String sign(@RequestParam(name = "mail") String mail, @RequestParam(name = "username") String username, @RequestParam(name = "password") String password, @RequestParam(name = "profile") MultipartFile profile) throws IOException {
        if (profile.isEmpty()) {
            System.out.println("wtf is this bogus");
            return "rediret:post?error";
        }

        if (creatorManager.findByUsername(username) != null){
            return "redirect:login";
        }

        byte[] bytes = profile.getBytes();
        String originalFilename = profile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + extension;
        Path uploadPath = Paths.get(uploadDir, uniqueFilename);
        Files.write(uploadPath, bytes);
        CreatorDTOADD creator = new CreatorDTOADD();
        creator.setUsername(username);
        creator.setPassword(password);
        creator.setProfile("/" + uniqueFilename);
        creator.setMail(mail);
        Role role = roleService.getRoleByRoleName("user");
        creator.setRole(role);
        creatorManager.addCreator(creator);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();
        return "redirect:login";
    }

    @GetMapping("/user/{username}")
    public String user(@PathVariable String username, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CreatorDTO creatorViewer = creatorManager.findByUsername(authentication.getName());
        CreatorDTO creatorDTO = creatorManager.findByUsername(username);

        if (creatorViewer == null || creatorDTO == null) {
            return "redirect:/index";
        }

        // Proper string comparison
        boolean isAdmin = "admin".equals(creatorViewer.getRole().getName());
        boolean isSameUser = authentication.getName().equals(creatorDTO.getUsername());

        if (!isSameUser && !isAdmin) {
            return "redirect:/index";
        }

        model.addAttribute("creator", creatorDTO);
        return "user";
    }

    @PostMapping("/user/{username}")
    public String postUser(@PathVariable String username, Model model, @RequestParam(name = "mail") String mail,@RequestParam(name = "name") String newname,@RequestParam(name = "profile", required = false) MultipartFile profile) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CreatorDTO creatorViewer = creatorManager.findByUsername(authentication.getName());
        CreatorDTO creatorDTO = creatorManager.findByUsername(username);

        if (creatorViewer == null || creatorDTO == null) {
            return "redirect:/index";
        }

        // Proper string comparison
        boolean isAdmin = "admin".equals(creatorViewer.getRole().getName());
        boolean isSameUser = authentication.getName().equals(creatorDTO.getUsername());

        if (!isSameUser && !isAdmin) {
            return "redirect:/index";
        }


        CreatorDTOADD creatorDTOADD = creatorMapper.CreatorToCreatorDTOADD(creatorMapper.CreatorDTOToCreator(creatorDTO));
        if (profile != null) {
            byte[] bytes = profile.getBytes();
            String originalFilename = profile.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFilename = UUID.randomUUID().toString() + extension;
            Path uploadPath = Paths.get(uploadDir, uniqueFilename);
            Files.write(uploadPath, bytes);
            creatorDTOADD.setProfile("/" + uniqueFilename);
        }
        System.out.println(creatorDTOADD.getId());

        creatorDTOADD.setMail(mail);
        creatorDTOADD.setUsername(newname);
        creatorManager.updateCreator(creatorDTOADD);


        return "redirect:/user/"+username;
    }

    @PostMapping("/delete/{username}")
    public String delete(@PathVariable(name = "username")String username){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CreatorDTO creatorViewer = creatorManager.findByUsername(authentication.getName());
        CreatorDTO creatorDTO = creatorManager.findByUsername(username);
        System.out.println(creatorViewer.getUsername());
        if (creatorViewer == null || creatorDTO == null) {
            return "redirect:/my-videos/"+username;
        }

        // Proper string comparison
        boolean isAdmin = "admin".equals(creatorViewer.getRole().getName());
        boolean isSameUser = authentication.getName().equals(creatorDTO.getUsername());

        if (!isSameUser && !isAdmin) {
            return "redirect:/index";
        }
        CreatorDTOADD creatorDTOADD = creatorMapper.CreatorToCreatorDTOADD(creatorMapper.CreatorDTOToCreator(creatorDTO));
        creatorManager.deleteCeator(creatorDTOADD.getId());
            System.out.println("yes");

        return "redirect:/index";
    }

}
