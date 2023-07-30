package com.hitzseb.billeterapp.controller;

import com.hitzseb.billeterapp.dto.OperationRequest;
import com.hitzseb.billeterapp.model.Operation;
import com.hitzseb.billeterapp.model.Transaction;
import com.hitzseb.billeterapp.response.DeleteResponse;
import com.hitzseb.billeterapp.response.OperationResponse;
import com.hitzseb.billeterapp.service.OperationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/operation")
@CrossOrigin("http://localhost:4200/")
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;

    @io.swagger.v3.oas.annotations.Operation(summary = "Retrieves operations for a logged-in user.",
            description = "Returns a list of operations related with the logged-in user." +
                    " Results can be optionally filtered by operation transaction, category, and date." +
                    " The `transaction`, `categoryId` and `date` parameters are used to specify filters." +
                    " If no filters are provided, all operations from the user will be returned.")
    @GetMapping("/all")
    @PreAuthorize("@operationService.operationBelongsToUser(authentication.principal.id, #id)")
    public ResponseEntity<?> getOperations(@RequestParam(required=false) Optional<Transaction> transaction,
                                           @RequestParam(required=false) Optional<Long> categoryId,
                                           @RequestParam(required=false)
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                           Optional<LocalDate> date) {
        try {
            List<Operation> operations = operationService.getAllOperations(transaction, categoryId, date);
            return ResponseEntity.ok(operations);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Creates a new operation.",
            description = "Creates a new operation with the specified details." +
                    " The `description`, `amount`, `transaction`, `categoryId`, and `date` parameters" +
                    " are used to specify the operation details." +
                    " Returns a response with a message indicating" +
                    " that the operation was created successfully" +
                    " and the operation details in the response body.")
    @PostMapping("/new")
    @PreAuthorize("@operationService.operationBelongsToUser(authentication.principal.id, #id)")
    public ResponseEntity<?> createOperation(@RequestBody Optional<OperationRequest> request) {
        try {
            Operation operation = operationService.saveOperation(request);
            OperationResponse response = new OperationResponse("Operation created successfully", operation);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Updates an existing operation.",
            description = "Updates an existing operation with the specified ID and details." +
                    " The `id` path variable is used to identify the operation to update," +
                    " and the `description`, `amount`, `transaction`, `categoryId`, and `date`" +
                    " parameters are used to specify the updated operation details." +
                    " Returns a response with a message indicating that the operation" +
                    " was updated successfully and the updated operation details in the response body." +
                    " The authenticated user must have the same ID as the operation being updated.")
    @PutMapping("/{id}/edit")
    @PreAuthorize("@operationService.operationBelongsToUser(authentication.principal.id, #id)")
    public ResponseEntity<?> editOperation(@PathVariable Long id,
                                           @RequestParam Optional<String> description,
                                           @RequestParam Optional<Double> amount,
                                           @RequestParam Optional<Transaction> transaction,
                                           @RequestParam Optional<Long> categoryId,
                                           @RequestParam Optional<LocalDate> date) {
        try {
            Operation operation = operationService.updateOperation(id, description, amount, transaction, categoryId, date);
            OperationResponse response = new OperationResponse("Operation updated successfully", operation);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Deletes an existing operation.",
            description = "Deletes an existing operation with the specified ID." +
                    " Returns a response with a message indicating that the operation" +
                    " was deleted successfully and the operation's `id`.")
    @DeleteMapping("/{id}/delete")
    @PreAuthorize("@operationService.operationBelongsToUser(authentication.principal.id, #id)")
    public ResponseEntity<?> deleteOperation(@PathVariable Long id) {
        try {
            operationService.deleteOperationById(id);
            DeleteResponse response = new DeleteResponse("Operation deleted successfully", id);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
