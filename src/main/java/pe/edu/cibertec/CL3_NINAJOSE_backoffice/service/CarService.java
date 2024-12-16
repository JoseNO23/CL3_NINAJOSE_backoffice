package pe.edu.cibertec.CL3_NINAJOSE_backoffice.service;

import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.CarDetailsDto;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.CarDto;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    List<CarDto> getAllCars();

    Optional<CarDetailsDto> getCarById(Integer id);

    boolean updateCar(CarDetailsDto carDetailsDto);

    boolean deleteCarById(Integer id);

    boolean createCar(CarDetailsDto carDetailsDto);

    Optional<Car> findById(Integer id);
}
