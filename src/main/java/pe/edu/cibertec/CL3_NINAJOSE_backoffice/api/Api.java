package pe.edu.cibertec.CL3_NINAJOSE_backoffice.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.CarDetailsDto;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.CarDto;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.response.*;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class Api {
    @Autowired
    private CarService carService;

    @GetMapping("/all")
    public FindCarsResponse getAllCars() {
        try {
            List<CarDto> cars = carService.getAllCars();
            return new FindCarsResponse("01", "Carros recuperados con éxito", cars);
        } catch (Exception e) {
            return new FindCarsResponse("02", "Error al recuperar los carros", null);
        }
    }

    @GetMapping("/{id}")
    public FindCarResponse getCarById(@PathVariable Integer id) {
        try {
            return carService.getCarById(id)
                    .map(car -> new FindCarResponse("01", "Carro recuperado con éxito", car))
                    .orElse(new FindCarResponse("02", "Carro no encontrado", null));
        } catch (Exception e) {
            return new FindCarResponse("03", "Error al recuperar el carro", null);
        }
    }


    @PostMapping
    public AddCarsResponse addCar(@RequestBody CarDetailsDto carDetailsDto) {
        try {
            boolean success = carService.createCar(carDetailsDto);
            if (success) {
                return new AddCarsResponse("01", "Carro creado con éxito");
            }
            return new AddCarsResponse("02", "El carro ya existe");
        } catch (Exception e) {
            return new AddCarsResponse("03", "Error al crear el carro");
        }
    }

    @PutMapping("/{id}")
    public UpdateCarsResponse updateCar(@PathVariable Integer id, @RequestBody CarDetailsDto carDetailsDto) {try {
        boolean success = carService.updateCar(carDetailsDto);
        if (success) {
            return new UpdateCarsResponse("01", "Carro actualizado con éxito");
        }
        return new UpdateCarsResponse("02", "Carro no encontrado");
    } catch (Exception e) {
        return new UpdateCarsResponse("03", "Error al actualizar el carro");
    }
    }

    @DeleteMapping("/{id}")
    public DeleteCarsResponse deleteCar(@PathVariable Integer id) {
        try {
            boolean success = carService.deleteCarById(id);
            if (success) {
                return new DeleteCarsResponse("01", "Carro eliminado con éxito");
            }
            return new DeleteCarsResponse("02", "Carro no encontrado");
        } catch (Exception e) {
            return new DeleteCarsResponse("03", "Error al eliminar el carro");
        }
    }
}
