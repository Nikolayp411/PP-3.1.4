package web.service;

import java.util.List;
import web.model.AppPage;
import web.model.User;

public interface PageService {
    List<AppPage> getAvailablePages(User user);
}
