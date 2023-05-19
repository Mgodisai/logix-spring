package hu.alagi.logixspring.exception;

public class InvalidCountryCodeException extends RuntimeException {

    public InvalidCountryCodeException(String invalidCountryCode) {
        super("Invalid country code: "+invalidCountryCode);
    }
}
