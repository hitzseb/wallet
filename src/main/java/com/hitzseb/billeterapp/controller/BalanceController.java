package com.hitzseb.billeterapp.controller;

import com.hitzseb.billeterapp.response.BalanceResponse;
import com.hitzseb.billeterapp.service.BalanceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:4200/")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService service;

    @Operation(summary = "Retrieves logged-in user's balance.",
            description = "Retrieves logged-in user's total profits, expenses and balance.")
    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> getBalance() {
        BalanceResponse response = new BalanceResponse(
                service.getProfits(),
                service.getExpenses(),
                service.getBalance()
        );
        return ResponseEntity.ok(response);
    }

}
