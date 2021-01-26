package pl.polsl.webexchange.errorhandling;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(String message){
        super(message);
    }
}
