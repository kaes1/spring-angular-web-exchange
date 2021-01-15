package pl.polsl.webexchange.errorhandling;

public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException(String message){
        super(message);
    }
}
