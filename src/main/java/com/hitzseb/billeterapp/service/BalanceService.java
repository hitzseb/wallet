package com.hitzseb.billeterapp.service;

import com.hitzseb.billeterapp.model.Operation;
import com.hitzseb.billeterapp.model.Transaction;
import com.hitzseb.billeterapp.model.User;
import com.hitzseb.billeterapp.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final OperationRepository operationRepository;

    public Double getProfits() {
        double total = getTotalAmountByTransaction(Transaction.PROFIT);
        return total;
    }

    public Double getExpenses() {
        double total = getTotalAmountByTransaction(Transaction.EXPENSE);
        return total;
    }

    public Double getBalance() {
        double total = getTotalAmountByTransaction(Transaction.PROFIT) - getTotalAmountByTransaction(Transaction.EXPENSE);
        return total;
    }

    public double getTotalAmountByTransaction(Transaction transaction) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Operation> operations = operationRepository.findAllByUser(user);
        double total = operations.stream()
                .filter(o -> o.getTransaction() == transaction)
                .mapToDouble(Operation::getAmount)
                .sum();
        return total;
    }

}
