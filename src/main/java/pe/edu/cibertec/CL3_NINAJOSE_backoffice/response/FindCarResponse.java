package pe.edu.cibertec.CL3_NINAJOSE_backoffice.response;

import lombok.Data;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.CarDetailsDto;

@Data
public class FindCarResponse {
    private String code;
    private String message;
    private CarDetailsDto car;

    public FindCarResponse(String code, String message, CarDetailsDto car) {
        this.code = code;
        this.message = message;
        this.car = car;
    }
}
