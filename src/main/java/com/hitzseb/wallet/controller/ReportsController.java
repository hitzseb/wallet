package com.hitzseb.wallet.controller;

import com.hitzseb.wallet.responses.ReportsResponse;
import com.hitzseb.wallet.service.ReportsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("https://hitzseb-wallet-wizard.web.app/")
@RequiredArgsConstructor
public class ReportsController {

    private final ReportsService service;

    @Operation(summary = "Retrieves logged-in user's reports.",
            description = "Retrieves categories and months" +
                    " ordered by profits and expenses" +
                    " in the form of 4 key-value list.")
    @GetMapping("/reports")
    public ResponseEntity<ReportsResponse> getReports() {
        ReportsResponse response = new ReportsResponse(
                service.findCategoriesByProfit(),
                service.findCategoriesByExpense(),
                service.findMonthsByProfit(),
                service.findMonthsByExpense());
        return ResponseEntity.ok(response);
    }

}
