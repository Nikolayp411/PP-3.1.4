package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.dao.UserDao;
import web.model.User;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userDao.listUsers();
        model.addAttribute("users", users);
        return "users";
    }
    @PostMapping("/users")
    public String addUser(@ModelAttribute("user") User user, @RequestParam String action) {
        if (user.getId() == null) {
            userDao.add(user);
        } else {
            userDao.update(user);
        }
        return "redirect:/users";
    }

    @PostMapping("/deleteUser")
    public String deleteUser (@RequestParam("userId") Long userId) {
        userDao.delete(userId);
        return "redirect:/users";
    }

    @GetMapping("/editUser")
    public String showEditUserForm(@RequestParam("userId") Long id, Model model) {
        User user = userDao.getUserById(id);
        model.addAttribute("user", user);
        return "editUser";
    }

}
