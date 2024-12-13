package web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.model.AppPage;
import web.model.User;
import web.service.PageService;
import web.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserProfileController {

    private final UserService userService;
    private final PageService pageService;


    public UserProfileController(UserService userService, PageService pageService) {
        this.userService = userService;
        this.pageService = pageService;
    }


    @GetMapping("/user")
    public ResponseEntity<User> getUser() {
        return new ResponseEntity<> (userService.getCurrentUser(), HttpStatus.OK);
    }

    @GetMapping("/available-pages")
    public ResponseEntity<List<AppPage>> getAvailablePages() {
        List<AppPage> availablePages = pageService.getAvailablePages(userService.getCurrentUser());
        return ResponseEntity.ok(availablePages);
    }


}
