package pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto;

import java.time.LocalDate;

public record CarDetailsDto(
        Integer carId,
        String make,
        String model,
        Integer year,
        String vin,
        String licensePlate,
        String ownerName,
        String ownerContact,
        LocalDate purchaseDate,
        Integer mileage,
        String engineType,
        String color,
        String insuranceCompany,
        String insurancePolicyNumber,
        LocalDate registrationExpirationDate,
        LocalDate serviceDueDate
    ) {
}
