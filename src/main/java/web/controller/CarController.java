package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.car_back.Car;

import java.util.Arrays;
import java.util.List;

@Controller
public class CarController {
    @GetMapping("/cars")
    public String showCars(@RequestParam(required = false, defaultValue = "5") Integer count, ModelMap model) {
        List<Car> cars = Arrays.asList(
                new Car("Toyota", "Camry", 2020),
                new Car("Honda", "Civic", 2019),
                new Car("Ford", "Focus", 2018),
                new Car("BMW", "3 Series", 2021),
                new Car("Audi", "A4", 2022)
        );

        model.addAttribute("cars", count > cars.size() ? cars : cars.subList(0, count));

        return "cars";
    }

}
