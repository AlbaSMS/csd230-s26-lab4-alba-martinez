package app.controllers;

import app.entities.PhoneEntity;
import app.repositories.PhoneRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Phone REST API", description = "JSON API for managing inventory")
@RestController
@RequestMapping("/api/rest/phones")
@CrossOrigin(origins = "*")
public class PhoneRestController {
    private final PhoneRepository repository;

    public PhoneRestController(PhoneRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Retrieve all phones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "phones found"),
    })
    public List<PhoneEntity> all() {
        return repository.findAll();
    }

    @PostMapping
    @Operation(summary = "Create a new phones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Phone created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public PhoneEntity newPhone(@RequestBody PhoneEntity newPhone) {
        return repository.save(newPhone);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a phone by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Phone found"),
            @ApiResponse(responseCode = "404", description = "Phone not found")
    })
    public PhoneEntity one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PhoneNotFoundException(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing phone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Phone updated"),
            @ApiResponse(responseCode = "404", description = "Phone not found")
    })
    public PhoneEntity replacePhone(@RequestBody PhoneEntity newPhone, @PathVariable Long id) {
        return repository.findById(id)
                .map(phone -> {
                    phone.setSupports5G(newPhone.isSupports5G());
                    phone.setCameraMP(newPhone.getCameraMP());
                    phone.setWarrantyMonths(newPhone.getWarrantyMonths());
                    phone.setBrand(newPhone.getBrand());
                    phone.setPrice(newPhone.getPrice());
                    return repository.save(phone);
                })
                .orElseGet(() -> {
                    newPhone.setId(id);
                    return repository.save(newPhone);
                });
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a phone by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Phone deleted"),
            @ApiResponse(responseCode = "404", description = "Phone not found")
    })
    public void deletePhone(@PathVariable Long id) {
        repository.deleteById(id);
    }
}