package app.controllers;

import app.entities.LaptopEntity;
import app.repositories.LaptopRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Laptop REST API", description = "JSON API for managing inventory")
@RestController
@RequestMapping("/api/rest/laptops")
@CrossOrigin(origins = "*")
public class LaptopRestController {
    private final LaptopRepository repository;
    public LaptopRestController(LaptopRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Retrieve all laptops")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "laptops found"),
    })
    public List<LaptopEntity> all() {
        return repository.findAll();
    }

    @PostMapping
    @Operation(summary = "Create a new laptops")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Laptop created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public LaptopEntity newLaptop(@RequestBody LaptopEntity newLaptop) {
        return repository.save(newLaptop);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a laptop by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Laptop found"),
            @ApiResponse(responseCode = "404", description = "Laptop not found")
    })
    public LaptopEntity one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new LaptopNotFoundException(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing laptop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Laptop updated"),
            @ApiResponse(responseCode = "404", description = "Laptop not found")
    })
    public LaptopEntity replaceLaptop(@RequestBody LaptopEntity newLaptop, @PathVariable Long id) {
        return repository.findById(id)
                .map(laptop -> {
                    laptop.setScreenSizeInches(newLaptop.getScreenSizeInches());
                    laptop.setRamGB(newLaptop.getRamGB());
                    laptop.setWarrantyMonths(newLaptop.getWarrantyMonths());
                    laptop.setBrand(newLaptop.getBrand());
                    laptop.setPrice(newLaptop.getPrice());
                    return repository.save(laptop);
                })
                .orElseGet(() -> {
                    newLaptop.setId(id);
                    return repository.save(newLaptop);
                });
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a laptop by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Laptop deleted"),
            @ApiResponse(responseCode = "404", description = "Laptop not found")
    })
    public void deleteLaptop(@PathVariable Long id) {
        repository.deleteById(id);
    }
}