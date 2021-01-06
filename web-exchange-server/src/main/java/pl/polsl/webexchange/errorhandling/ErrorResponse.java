package pl.polsl.webexchange.errorhandling;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ErrorResponse {
    Date timestamp;
    Integer status;
    String error;
    String message;
    String path;
}
