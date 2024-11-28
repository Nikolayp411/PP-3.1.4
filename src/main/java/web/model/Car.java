package web.model;

public class Car {
    private String manifacturer;
    private String model;
    private int year;

    public Car(String make, String model, int year) {
        this.manifacturer = make;
        this.model = model;
        this.year = year;
    }

    public String getManifacturer() {
        return manifacturer;
    }

    public void setManifacturer(String manifacturer) {
        this.manifacturer = manifacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

