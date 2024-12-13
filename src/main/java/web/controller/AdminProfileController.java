package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import web.model.AppPage;
import web.model.Role;
import web.model.User;
import web.repository.UserRepository;
import web.service.PageService;
import web.service.UserService;
import web.service.RoleService;

import java.util.*;

@RestController
@RequestMapping("/api/admin")
public class AdminProfileController {

    private final UserService userService;
    private final RoleService roleService;
    private final PageService pageService;
    private final UserRepository userRepository;

    @Autowired
    public AdminProfileController(UserService userService,
                                  RoleService roleService,
                                  PageService pageService,
                                  UserRepository userRepository) {
        this.userService = userService;
        this.roleService = roleService;
        this.pageService = pageService;
        this.userRepository = userRepository;
    }

    @GetMapping("/current-user")
    public ResponseEntity<User> getCurrentUser () {
        return ResponseEntity.ok(userService.getCurrentUser ());
    }

    @GetMapping("/available-pages")
    public ResponseEntity<List<AppPage>> getAvailablePages() {
        List<AppPage> availablePages = pageService.getAvailablePages(userService.getCurrentUser ());
        return ResponseEntity.ok(availablePages);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.listUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<Map<String, Object>> getUser (@PathVariable Long id) {
        User user = userService.getUserById(id);
        List<Role> availableRoles = roleService.listRoles();

        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("availableRoles", availableRoles);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/addUser")
    public ResponseEntity<Void> addUser (@RequestBody User user) {
        System.out.println("Attempting to add user: " + user);
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(new String(Base64.getDecoder().decode(user.getPassword()))));
            userService.addUser (user);
            System.out.println("User  added successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            System.err.println("Error adding user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<Void> deleteUser (@RequestBody Map<String, Long> payload) {
        Long userId = payload.get("userId");
        userService.deleteUser (userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody User user) {

        if (user.getId() == null) {
            return ResponseEntity.badRequest().body("The given id must not be null");
        }

        User existingUser  = userRepository.findById(userId).orElseThrow();

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setAge(user.getAge());
        existingUser.setRoles(user.getRoles());

        List<Role> updatedRoles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            if (role.getId() == null) {
                return ResponseEntity.badRequest().body("Role ID must not be null");
            }
            Role existingRole = roleService.getRoleById(role.getId());
            updatedRoles.add(existingRole);
        }
        existingUser.setRoles(updatedRoles);

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            existingUser .setPassword(hashedPassword);
        }

        userRepository.save(existingUser);

        return ResponseEntity.ok("{\"message\":\"User  updated successfully\"}");
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = roleService.listRoles();
        return ResponseEntity.ok(roles);
    }
}
