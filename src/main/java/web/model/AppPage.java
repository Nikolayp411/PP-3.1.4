package web.model;

public class AppPage {
    private String url;
    private String name;

    // Конструктор
    public AppPage(String url, String name) {
        this.url = url;
        this.name = name;
    }

    // Геттеры
    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    // Метод toString() (по желанию)
    @Override
    public String toString() {
        return "AppPage{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
