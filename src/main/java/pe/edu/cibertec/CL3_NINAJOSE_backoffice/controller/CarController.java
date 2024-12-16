package pe.edu.cibertec.CL3_NINAJOSE_backoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.CarDetailsDto;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.CarDto;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.entity.Car;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.repository.CarRepository;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.service.CarService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/list")
    public String listCars(Model model, @RequestParam(value = "error", required = false) String error) {
        model.addAttribute("error", error);
        model.addAttribute("cars", carService.getAllCars());
        return "cars";
    }

    @GetMapping("/detail/{id}")
    public String getCarDetail(@PathVariable Integer id, Model model) {
        Optional<Car> car = carService.findById(id);
        if (car.isPresent()) {
            model.addAttribute("car", car.get());
            return "cars-detail";
        } else {
            return "redirect:/cars/list?error=Car+not+found";
        }
    }

    @GetMapping("/add")
    public String addCarForm(Model model) {
        model.addAttribute("car", new CarDetailsDto(
                null, "", "", null, "", "", "", "",
                null, null, "", "", "", "", null, null
        ));
        return "cars-add";
    }

    @PostMapping("/add")
    public String addCar(@ModelAttribute CarDetailsDto carDetailsDto) {
        try {
            carService.createCar(carDetailsDto);
        } catch (Exception e) {
            return "redirect:/cars/add?error=Unable+to+add+car";
        }
        return "redirect:/cars/list";
    }

    @GetMapping("/edit/{carId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editCarForm(@PathVariable("carId") Integer carId, Model model) {
        return carService.getCarById(carId)
                .map(car -> {
                    model.addAttribute("car", car);
                    return "cars-edit";
                })
                .orElse("redirect:/cars/list?error=Car+not+found");
    }

    @PostMapping("/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editCar(@ModelAttribute CarDetailsDto carDetailsDto) {
        carService.updateCar(carDetailsDto);
        return "redirect:/cars/list";
    }

    @GetMapping("/delete/{carId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteCar(@PathVariable("carId") Integer carId) {
        carService.deleteCarById(carId);
        return "redirect:/cars/list";
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateCar(@PathVariable Integer id, Model model) {
        Optional<Car> car = carService.findById(id);
        if (car.isPresent()) {
            model.addAttribute("car", car.get());
            return "cars-update";
        }
        return "redirect:/cars/list?error=Car+not+found";
    }
}
