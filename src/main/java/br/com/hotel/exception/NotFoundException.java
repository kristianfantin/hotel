package br.com.hotel.exception;


public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("No record found");
    }

    public NotFoundException(final Long id) {
        super("No record found for id " + id);
    }

    public NotFoundException(String message) {
        super(message);
    }

}
