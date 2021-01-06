package pl.polsl.webexchange.errorhandling;

public class NotUniqueException extends RuntimeException {
    public NotUniqueException(String message){
        super(message);
    }
}
