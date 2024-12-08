package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.AppPage;
import web.model.Role;
import web.model.User;
import web.service.UserService;
import web.service.RoleService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminProfileController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminProfileController(UserService userService,
                                  RoleService roleService,
                                  PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String adminPanel(@RequestParam(value = "view", required = false, defaultValue = "table") String view, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails currentUser = (UserDetails) authentication.getPrincipal();
            model.addAttribute("currentUser", currentUser);
        }

        List<User> users = userService.listUsers();
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        List<Role> roles = roleService.listRoles();
        model.addAttribute("roles", roles);

        List<AppPage> availablePages = new ArrayList<>();
        availablePages.add(new AppPage("/admin", "Admin"));
        availablePages.add(new AppPage("/user", "User"));
        model.addAttribute("availablePages", availablePages);
        model.addAttribute("currentPageUrl", "/admin");

        model.addAttribute("view", view);
        return "admin";
    }

    @GetMapping("/getUser")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getUser (@RequestParam Long id) {
        User user = userService.getUserById(id);
        List<Role> availableRoles = roleService.listRoles();

        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("availableRoles", availableRoles);

        return ResponseEntity.ok(response);
    }



    @PostMapping
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam("password") String password,
                          @RequestParam("roleIds") List<Long> roleIds) {
        user.setRoles(roleService.getRolesById(roleIds));
        user.setPassword(passwordEncoder.encode(password));
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<Void> deleteUser (@RequestBody Map<String, Long> payload) {
        Long userId = payload.get("userId");
        userService.deleteUser (userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam("password") String password,
                             @RequestParam("roleIds") List<Long> roleIds) {
        user.setRoles(roleService.getRolesById(roleIds));
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }
}

