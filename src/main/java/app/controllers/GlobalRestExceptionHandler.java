package app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @RestControllerAdvice tells Spring that this class is used to
 * render JSON specifically for error scenarios across ALL controllers.
 */
@RestControllerAdvice
class GlobalRestExceptionHandler {

    /**
     * @ExceptionHandler triggers this method ONLY when a NotFoundException occurs.
     * @ResponseStatus(HttpStatus.NOT_FOUND) ensures the browser/Postman
     * receives a true 404 status code in the header.
     */
    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String bookNotFoundHandler(BookNotFoundException ex) {
        // We return the raw message string.
        // Spring converts this into a JSON-compatible body.
        return ex.getMessage();
    }
    @ExceptionHandler(MagazineNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String magazineNotFoundHandler(MagazineNotFoundException ex) {
        // We return the raw message string.
        // Spring converts this into a JSON-compatible body.
        return ex.getMessage();
    }
    @ExceptionHandler(DiscMagNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String discMagNotFoundHandler(DiscMagNotFoundException ex) {
        // We return the raw message string.
        // Spring converts this into a JSON-compatible body.
        return ex.getMessage();
    }
    @ExceptionHandler(TicketNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String ticketNotFoundHandler(TicketNotFoundException ex) {
        // We return the raw message string.
        // Spring converts this into a JSON-compatible body.
        return ex.getMessage();
    }
    @ExceptionHandler(LaptopNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String laptopNotFoundHandler(LaptopNotFoundException ex) {
        // We return the raw message string.
        // Spring converts this into a JSON-compatible body.
        return ex.getMessage();
    }
    @ExceptionHandler(PhoneNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String phoneNotFoundHandler(PhoneNotFoundException ex) {
        // We return the raw message string.
        // Spring converts this into a JSON-compatible body.
        return ex.getMessage();
    }
}