package app.controllers;

import app.entities.DiscMagEntity;
import app.repositories.DiscMagEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "DiscMag REST API", description = "JSON API for managing inventory")
@RestController
@RequestMapping("/api/rest/discMags")
@CrossOrigin(origins = "*")
public class DiscMagRestController {
    private final DiscMagEntityRepository repository;
    public DiscMagRestController(DiscMagEntityRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Retrieve all discMags")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "discMags found"),
    })
    public List<DiscMagEntity> all() {
        return repository.findAll();
    }

    @PostMapping
    @Operation(summary = "Create a new discMags")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "DiscMag created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public DiscMagEntity newDiscMag(@RequestBody DiscMagEntity newDiscMag) {
        return repository.save(newDiscMag);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a discMag by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DiscMag found"),
            @ApiResponse(responseCode = "404", description = "DiscMag not found")
    })
    public DiscMagEntity one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DiscMagNotFoundException(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing discMag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DiscMag updated"),
            @ApiResponse(responseCode = "404", description = "DiscMag not found")
    })
    public DiscMagEntity replaceDiscMag(@RequestBody DiscMagEntity newDiscMag, @PathVariable Long id) {
        return repository.findById(id)
                .map(discMag -> {
                    discMag.setTitle(newDiscMag.getTitle());
                    discMag.setCurrentIssue(newDiscMag.getCurrentIssue());
                    discMag.setOrderQty(newDiscMag.getOrderQty());
                    discMag.setPrice(newDiscMag.getPrice());
                    discMag.setHasDisc(newDiscMag.isHasDisc());
                    discMag.setCopies(newDiscMag.getCopies());
                    return repository.save(discMag);
                })
                .orElseGet(() -> {
                    newDiscMag.setId(id);
                    return repository.save(newDiscMag);
                });
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a discMag by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DiscMag deleted"),
            @ApiResponse(responseCode = "404", description = "DiscMag not found")
    })
    public void deleteDiscMag(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
