package pl.polsl.webexchange.errorhandling;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message){
        super(message);
    }
}
