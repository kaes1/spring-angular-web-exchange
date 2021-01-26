package pl.polsl.webexchange.errorhandling;

public class InvalidCurrencyRateException extends RuntimeException {
    public InvalidCurrencyRateException(String message){
        super(message);
    }
}
