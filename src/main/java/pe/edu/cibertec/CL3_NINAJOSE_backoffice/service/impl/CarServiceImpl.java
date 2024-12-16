package pe.edu.cibertec.CL3_NINAJOSE_backoffice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.CarDetailsDto;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.CarDto;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.entity.Car;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.repository.CarRepository;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.service.CarService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public List<CarDto> getAllCars() {
        List<CarDto> cars = new ArrayList<>();
        carRepository.findAll().forEach(car -> cars.add(
                new CarDto(
                        car.getCarId(),
                        car.getMake(),
                        car.getModel(),
                        car.getYear(),
                        car.getColor(),
                        car.getOwnerName()
                )
        ));
        return cars;
    }

    @Override
    public Optional<CarDetailsDto> getCarById(Integer id) {
        return carRepository.findById(id).map(car -> new CarDetailsDto(
                car.getCarId(), car.getMake(), car.getModel(), car.getYear(), car.getVin(),
                car.getLicensePlate(), car.getOwnerName(), car.getOwnerContact(),
                car.getPurchaseDate(), car.getMileage(), car.getEngineType(),
                car.getColor(), car.getInsuranceCompany(), car.getInsurancePolicyNumber(),
                car.getRegistrationExpirationDate(), car.getServiceDueDate()
        ));
    }

    @Override
    public boolean updateCar(CarDetailsDto carDetailsDto) {
        return carRepository.findById(carDetailsDto.carId()).map(car -> {
            car.setMake(carDetailsDto.make());
            car.setModel(carDetailsDto.model());
            car.setYear(carDetailsDto.year());
            car.setVin(carDetailsDto.vin());
            car.setLicensePlate(carDetailsDto.licensePlate());
            car.setOwnerName(carDetailsDto.ownerName());
            car.setOwnerContact(carDetailsDto.ownerContact());
            car.setPurchaseDate(carDetailsDto.purchaseDate());
            car.setMileage(carDetailsDto.mileage());
            car.setEngineType(carDetailsDto.engineType());
            car.setColor(carDetailsDto.color());
            car.setInsuranceCompany(carDetailsDto.insuranceCompany());
            car.setInsurancePolicyNumber(carDetailsDto.insurancePolicyNumber());
            car.setRegistrationExpirationDate(carDetailsDto.registrationExpirationDate());
            car.setServiceDueDate(carDetailsDto.serviceDueDate());  // Corregido el error tipográfico
            carRepository.save(car);  // Corregido el error con scarRepository
            return true;
        }).orElse(false);
    }

    @Override
    public boolean deleteCarById(Integer id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
            return true;
        }
        return false;
    }



    @Override
    public boolean createCar(CarDetailsDto carDetailsDto) {
        if (carDetailsDto == null) {
            throw new IllegalArgumentException("El objeto CarDetailsDto no puede ser nulo.");
        }
        Car car = new Car();
        car.setMake(carDetailsDto.make());
        car.setModel(carDetailsDto.model());
        car.setYear(carDetailsDto.year());
        car.setOwnerName(carDetailsDto.ownerName());
        // Mapea los demás campos según sea necesario.
        carRepository.save(car);
        return true;
    }


    public Optional<Car> findById(Integer id) {
        return carRepository.findById(id);
    }


    @Override
    public CarDetailsDto getCarDetailsById(int carId) {
        return carRepository.findById(carId)
                .map(car -> new CarDetailsDto(
                        car.getCarId(),
                        car.getMake(),
                        car.getModel(),
                        car.getYear(),
                        car.getVin(),
                        car.getLicensePlate(),
                        car.getOwnerName(),
                        car.getOwnerContact(),
                        car.getPurchaseDate(),
                        car.getMileage(),
                        car.getEngineType(),
                        car.getColor(),
                        car.getInsuranceCompany(),
                        car.getInsurancePolicyNumber(),
                        car.getRegistrationExpirationDate(),
                        car.getServiceDueDate()
                ))
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + carId)); // Usa RuntimeException
    }
}
