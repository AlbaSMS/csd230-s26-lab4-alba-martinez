package app.controllers;

import app.entities.BookEntity;
import app.repositories.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Book REST API", description = "JSON API for managing inventory")
@RestController
@RequestMapping("/api/rest/books")
@CrossOrigin(origins = "*")
public class BookRestController {
    private final BookRepository repository;

    public BookRestController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Retrieve all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books found"),
    })
    public List<BookEntity> all() {
        return repository.findAll();
    }

    @PostMapping
    @Operation(summary = "Create a new book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public BookEntity newBook(@RequestBody BookEntity newBook) {
        return repository.save(newBook);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public BookEntity one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public BookEntity replaceBook(@RequestBody BookEntity newBook, @PathVariable Long id) {
        return repository.findById(id)
                .map(book -> {
                    book.setTitle(newBook.getTitle());
                    book.setAuthor(newBook.getAuthor());
                    book.setPrice(newBook.getPrice());
                    book.setCopies(newBook.getCopies());
                    return repository.save(book);
                })
                .orElseGet(() -> {
                    newBook.setId(id);
                    return repository.save(newBook);
                });
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book deleted"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public void deleteBook(@PathVariable Long id) {
        repository.deleteById(id);
    }
}