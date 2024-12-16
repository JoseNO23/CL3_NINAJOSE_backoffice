package pe.edu.cibertec.CL3_NINAJOSE_backoffice.response;

import lombok.Data;

@Data
public class DeleteCarsResponse {
    private String code;
    private String message;

    public DeleteCarsResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
