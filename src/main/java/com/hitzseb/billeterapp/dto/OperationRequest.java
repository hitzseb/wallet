package com.hitzseb.billeterapp.dto;

import com.hitzseb.billeterapp.model.Transaction;
import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class OperationRequest {

    private Optional<String> description;
    private Optional<Double> amount;
    private Optional<Transaction> transaction;
    private Optional<Long> categoryId;
    private Optional<LocalDate> date;

}
