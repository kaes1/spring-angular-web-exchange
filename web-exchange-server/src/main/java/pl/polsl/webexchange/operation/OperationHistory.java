package pl.polsl.webexchange.operation;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class OperationHistory {
    LocalDateTime dateTime;
    String operationMessage;
}
