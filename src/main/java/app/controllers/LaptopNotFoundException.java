package app.controllers;

/**
 * A custom RuntimeException used specifically for our API.
 * By extending RuntimeException, we don't force our controllers
 * to use 'throws' clauses, keeping the code clean.
 */
public class LaptopNotFoundException extends RuntimeException {
    public LaptopNotFoundException(Long id) {
        super("Could not find laptop with ID: " + id);
    }
}