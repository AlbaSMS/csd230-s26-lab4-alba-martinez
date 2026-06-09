package app.controllers;

/**
 * A custom RuntimeException used specifically for our API.
 * By extending RuntimeException, we don't force our controllers
 * to use 'throws' clauses, keeping the code clean.
 */
public class PhoneNotFoundException extends RuntimeException {
    public PhoneNotFoundException(Long id) {
        super("Could not find phone with ID: " + id);
    }
}