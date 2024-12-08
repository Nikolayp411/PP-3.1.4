package web.model;

public class AppPage {
    private final String url;
    private final String name;

    public AppPage(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "AppPage{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
