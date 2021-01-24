package pl.polsl.webexchange.operation;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class OperationHistory {
    Long id;
    LocalDateTime dateTime;
    String operationMessage;
}
