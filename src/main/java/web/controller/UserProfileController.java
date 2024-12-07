package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String userProfile(Model model, Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        if (user.getRoles() == null) {
            user.setRoles(new ArrayList<>());
        }

        model.addAttribute("user", user);

        User currentUser  = userService.findByEmail(email); // Fetching current user again for the top bar
        model.addAttribute("currentUser", currentUser );

        // Формируем список доступных страниц для пользователя
        List<AppPage> availablePages = new ArrayList<>();
        availablePages.add(new AppPage("/user", "Profile"));
        if (user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))) {
            availablePages.add(new AppPage("/admin", "Admin Panel"));
        }
        // Добавьте другие страницы для пользователя

        model.addAttribute("availablePages", availablePages);

        return "user";
    }
}
