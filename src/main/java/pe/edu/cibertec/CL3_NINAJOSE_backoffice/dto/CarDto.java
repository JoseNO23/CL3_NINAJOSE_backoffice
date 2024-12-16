package pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto;


public record CarDto(
        Integer carId,
        String make,
        String model,
        Integer year,
        String Color,
        String ownerName) {
}

