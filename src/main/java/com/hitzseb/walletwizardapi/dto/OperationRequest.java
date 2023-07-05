package com.hitzseb.walletwizardapi.dto;

import com.hitzseb.walletwizardapi.enums.OperationType;
import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class OperationRequest {
    private Optional<String> description;
    private Optional<Double> amount;
    private Optional<OperationType> type;
    private Optional<Long> categoryId;
    private Optional<LocalDate> date;
}
