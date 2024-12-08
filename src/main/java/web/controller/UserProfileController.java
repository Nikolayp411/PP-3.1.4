package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.model.AppPage;
import web.model.User;
import web.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserProfileController {

    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String userProfile(Model model, Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        if (user.getRoles() == null) {
            user.setRoles(new ArrayList<>());
        }

        model.addAttribute("user", user);

        User currentUser  = userService.findByEmail(email);
        model.addAttribute("currentUser", currentUser );

        List<AppPage> availablePages = new ArrayList<>();
        if (user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))) {
            availablePages.add(new AppPage("/admin", "Admin"));
        }
        availablePages.add(new AppPage("/user", "User"));

        model.addAttribute("availablePages", availablePages);
        model.addAttribute("currentPageUrl", "/user");

        return "user";
    }
}
