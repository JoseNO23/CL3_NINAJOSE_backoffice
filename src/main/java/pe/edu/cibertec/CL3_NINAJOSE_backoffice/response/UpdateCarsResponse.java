package pe.edu.cibertec.CL3_NINAJOSE_backoffice.response;

import lombok.Data;

@Data
public class UpdateCarsResponse {
    private String code;
    private String message;

    public UpdateCarsResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
