package app.controllers;

import app.entities.MagazineEntity;
import app.repositories.MagazineEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Magazine REST API", description = "JSON API for managing inventory")
@RestController
@RequestMapping("/api/rest/magazines")
@CrossOrigin(origins = "*")
public class MagazineRestController {
    private final MagazineEntityRepository repository;
    public MagazineRestController(MagazineEntityRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Retrieve all magazines")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Magazines found"),
    })
    public List<MagazineEntity> all() {
        return repository.findAll();
    }

    @PostMapping
    @Operation(summary = "Create a new magazines")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Magazine created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public MagazineEntity newMagazine(@RequestBody MagazineEntity newMagazine) {
        return repository.save(newMagazine);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a magazine by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Magazine found"),
            @ApiResponse(responseCode = "404", description = "Magazine not found")
    })
    public MagazineEntity one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new MagazineNotFoundException(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing magazine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Magazine updated"),
            @ApiResponse(responseCode = "404", description = "Magazine not found")
    })
    public MagazineEntity replaceMagazine(@RequestBody MagazineEntity newMagazine, @PathVariable Long id) {
        return repository.findById(id)
                .map(magazine -> {
                    magazine.setTitle(newMagazine.getTitle());
                    magazine.setCurrentIssue(newMagazine.getCurrentIssue());
                    magazine.setOrderQty(newMagazine.getOrderQty());
                    magazine.setPrice(newMagazine.getPrice());
                    magazine.setCopies(newMagazine.getCopies());
                    return repository.save(magazine);
                })
                .orElseGet(() -> {
                    newMagazine.setId(id);
                    return repository.save(newMagazine);
                });
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a magazine by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Magazine deleted"),
            @ApiResponse(responseCode = "404", description = "Magazine not found")
    })
    public void deleteMagazine(@PathVariable Long id) {
        repository.deleteById(id);
    }
}