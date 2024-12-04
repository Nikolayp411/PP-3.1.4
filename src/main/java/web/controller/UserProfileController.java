package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.model.User;
import web.service.UserProfileService;

@Controller
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/user")
    public String userProfile(Model model, Authentication authentication) {
        String email = authentication.getName();
        User user = userProfileService.findByEmail(email);
        model.addAttribute("user", user);
        return "user";
    }
}
