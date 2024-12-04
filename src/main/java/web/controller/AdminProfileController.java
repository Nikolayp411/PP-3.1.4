package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserService;
import web.service.RoleService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    public String adminPanel(Model model) {
        List<User> users = userService.listUsers();
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        List<Role> roles = roleService.listRoles();
        model.addAttribute("roles", roles);
        return "admin";
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
    public String deleteUser(@RequestParam("userId") Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin";
    }

    @GetMapping("/editUser")
    public String showEditUserForm(@RequestParam("userId") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        List<Role> roles = roleService.listRoles();
        model.addAttribute("roles", roles);
        return "editUser";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam("password") String password,
                             @RequestParam("roleIds") List<Long> roleIds) {
        user.setRoles(roleService.getRolesById(roleIds));
        user.setPassword(passwordEncoder.encode(password));
        userService.updateUser(user);
        return "redirect:/admin";
    }
}

