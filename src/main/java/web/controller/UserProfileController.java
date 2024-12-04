package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.model.User;
import web.service.UserService;

import java.util.ArrayList;

@Controller
public class UserProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String userProfile(Model model, Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        // Убедитесь, что роли инициализированы
        if (user.getRoles() == null) {
            user.setRoles(new ArrayList<>()); // Инициализируем пустой список, если он null
        }

        model.addAttribute("user", user);
        return "user";
    }
}
