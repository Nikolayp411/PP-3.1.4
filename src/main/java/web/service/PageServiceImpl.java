package web.service;

import org.springframework.stereotype.Service;
import web.model.AppPage;
import web.model.User;
import java.util.ArrayList;
import java.util.List;

@Service
public class PageServiceImpl implements PageService {

    @Override
    public List<AppPage> getAvailablePages(User user) {
        List<AppPage> availablePages = new ArrayList<>();

        if (user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))) {
            availablePages.add(new AppPage("/admin", "Admin"));
        }

        availablePages.add(new AppPage("/user", "User"));

        return availablePages;
    }
}
