package pe.edu.cibertec.CL3_NINAJOSE_backoffice.response;

import lombok.Data;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.CarDto;

import java.util.List;

@Data
public class FindCarsResponse {
    private String code;
    private String message;
    private List<CarDto> cars;

    public FindCarsResponse(String code, String message, List<CarDto> cars) {
        this.code = code;
        this.message = message;
        this.cars = cars;
    }
}
