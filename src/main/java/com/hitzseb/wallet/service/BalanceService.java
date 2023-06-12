package com.hitzseb.wallet.service;

import com.hitzseb.wallet.model.Operation;
import com.hitzseb.wallet.enums.OperationType;
import com.hitzseb.wallet.model.User;
import com.hitzseb.wallet.repo.OperationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final OperationRepo operationRepo;

    public Double getProfits() {
        double total = getTotalAmountByType(OperationType.PROFIT);
        return total;
    }

    public Double getExpenses() {
        double total = getTotalAmountByType(OperationType.EXPENSE);
        return total;
    }

    public Double getBalance() {
        double total = getTotalAmountByType(OperationType.PROFIT) - getTotalAmountByType(OperationType.EXPENSE);
        return total;
    }

    public double getTotalAmountByType(OperationType type) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Operation> operations = operationRepo.findAllByUser(user);
        double total = operations.stream()
                .filter(o -> o.getType() == type)
                .mapToDouble(Operation::getAmount)
                .sum();
        return total;
    }

}
