package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.Role;
import web.model.User;
import web.model.UserLoginInfo;
import web.service.UserLoginInfoService;
import web.service.UserService;
import web.service.RoleService;

import java.util.List;

@Controller
public class AdminProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserLoginInfoService userLoginInfoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/admin")
    public String adminPanel(Model model) {
        List<User> users = userService.listUsers();
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        List<Role> roles = roleService.listRoles();
        model.addAttribute("roles", roles);
        return "admin";
    }

    @PostMapping("/admin")
    public String addUser(@ModelAttribute("user") User user, @RequestParam("password") String password) {
        Role role = roleService.getRoleById(user.getRole().getId());
        user.setRole(role);

        userService.addUser(user);

        UserLoginInfo userLoginInfo = new UserLoginInfo(user, user.getEmail(), passwordEncoder.encode(password));
        userLoginInfoService.addUserLoginInfo(userLoginInfo);

        return "redirect:/admin";
    }

    @PostMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam("userId") Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin";
    }

    @GetMapping("/admin/editUser")
    public String showEditUserForm(@RequestParam("userId") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        List<Role> roles = roleService.listRoles();
        model.addAttribute("roles", roles);
        return "editUser";
    }

    @PostMapping("/admin/updateUser")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("password") String password) {
        Role role = roleService.getRoleById(user.getRole().getId());
        user.setRole(role);

        userService.updateUser(user);

        UserLoginInfo userLoginInfo = userLoginInfoService.findByUserId(user.getId());
        userLoginInfo.setPassword(passwordEncoder.encode(password));
        userLoginInfoService.updateUserLoginInfo(userLoginInfo);

        return "redirect:/admin";
    }
}

