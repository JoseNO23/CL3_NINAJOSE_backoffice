package pe.edu.cibertec.CL3_NINAJOSE_backoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.CarDetailsDto;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.service.CarService;

@Controller
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/list")
    public String listCars(Model model, @RequestParam(value = "error", required = false) String error) {
        model.addAttribute("error", error);
        model.addAttribute("cars", carService.getAllCars());
        return "cars";
    }

    @GetMapping("/details/{id}")
    public String carDetails(@PathVariable("id") int id, Model model) {
        try {
            CarDetailsDto carDetails = carService.getCarDetailsById(id);
            model.addAttribute("car", carDetails);
            return "cars-detail";
        } catch (Exception e) {
            model.addAttribute("error", "No se encontró el vehículo: " + e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/add")
    public String addCarForm(Model model) {
        // Asegúrate de inicializar un objeto vacío para el formulario
        model.addAttribute("car", new CarDetailsDto(
                null, "", "", null, "", "", "", "",
                null, null, "", "", "", "", null, null
        ));
        return "cars-add";
    }

    @PostMapping("/add")
    public String addCar(@ModelAttribute CarDetailsDto carDetailsDto, Model model) {
        try {
            boolean isCreated = carService.createCar(carDetailsDto);
            if (!isCreated) {
                throw new RuntimeException("El vehículo ya existe o no se pudo guardar.");
            }
            return "redirect:/cars/list";
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo agregar el vehículo: " + e.getMessage());
            return "cars-add";
        }
    }


    @GetMapping("/edit/{carId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editCarForm(@PathVariable("carId") Integer carId, Model model) {
        return carService.getCarById(carId)
                .map(car -> {
                    model.addAttribute("car", car);
                    return "cars-update";
                })
                .orElse("redirect:/cars/list?error=Car+not+found");
    }

    @PostMapping("/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editCar(@ModelAttribute CarDetailsDto carDetailsDto, Model model) {
        try {
            carService.updateCar(carDetailsDto);
            return "redirect:/cars/list";
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo actualizar el vehículo: " + e.getMessage());
            return "cars-update";
        }
    }

    @PostMapping("/delete/{carId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteCar(@PathVariable("carId") Integer carId, Model model) {
        try {
            boolean isDeleted = carService.deleteCarById(carId);
            if (!isDeleted) {
                throw new RuntimeException("No se pudo eliminar el vehículo. ID no encontrado.");
            }
            return "redirect:/cars/list";
        } catch (Exception e) {
            model.addAttribute("error", "Error al eliminar el vehículo: " + e.getMessage());
            return "redirect:/cars/list";
        }
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateCarForm(@PathVariable("id") Integer id, Model model) {
        return carService.getCarById(id)
                .map(car -> {
                    model.addAttribute("car", car);
                    return "cars-update";
                })
                .orElse("redirect:/cars/list?error=Car+not+found");
    }
}
