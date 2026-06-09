package app.controllers;

import app.entities.TicketEntity;
import app.repositories.TicketEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ticket REST API", description = "JSON API for managing inventory")
@RestController
@RequestMapping("/api/rest/tickets")
@CrossOrigin(origins = "*")
public class TicketRestController {
    private final TicketEntityRepository repository;
    public TicketRestController(TicketEntityRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Retrieve all tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "tickets found"),
    })
    public List<TicketEntity> all() {
        return repository.findAll();
    }

    @PostMapping
    @Operation(summary = "Create a new tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public TicketEntity newTicket(@RequestBody TicketEntity newTicket) {
        return repository.save(newTicket);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a ticket by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket found"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    public TicketEntity one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket updated"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    public TicketEntity replaceTicket(@RequestBody TicketEntity newTicket, @PathVariable Long id) {
        return repository.findById(id)
                .map(ticket -> {
                    ticket.setDescription(newTicket.getDescription());
                    ticket.setPrice(newTicket.getPrice());
                    return repository.save(ticket);
                })
                .orElseGet(() -> {
                    newTicket.setId(id);
                    return repository.save(newTicket);
                });
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a ticket by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket deleted"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    public void deleteTicket(@PathVariable Long id) {
        repository.deleteById(id);
    }
}