package web.service;

import org.springframework.stereotype.Service;
import web.model.Car;

import java.util.Arrays;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private final List<Car> cars = Arrays.asList(
            new Car("Toyota", "Camry", 2020),
            new Car("Honda", "Civic", 2019),
            new Car("Ford", "Focus", 2018),
            new Car("BMW", "3 Series", 2021),
            new Car("Audi", "A4", 2022)
    );

    @Override
    public List<Car> getCars(Integer count) {
        return count > cars.size() ? cars : cars.subList(0, count);
    }
}