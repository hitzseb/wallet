package com.hitzseb.wallet.service;

import com.hitzseb.wallet.dto.CategoryAmount;
import com.hitzseb.wallet.dto.MonthAmount;
import com.hitzseb.wallet.enums.OperationType;
import com.hitzseb.wallet.model.Category;
import com.hitzseb.wallet.model.User;
import com.hitzseb.wallet.repo.CategoryRepo;
import com.hitzseb.wallet.repo.OperationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportsService {

    private final CategoryRepo categoryRepo;
    private final OperationRepo operationRepo;

    public List<CategoryAmount> findCategoriesByProfit() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Object[]> results = categoryRepo.findCategoriesByAmount(user, OperationType.PROFIT);
        List<CategoryAmount> categoryAmounts = new ArrayList<>();

        for (Object[] row : results) {
            Category category = (Category) row[0];
            Double totalAmount = (Double) row[1];
            categoryAmounts.add(new CategoryAmount(category, totalAmount));
        }

        return categoryAmounts;
    }


    public List<CategoryAmount> findCategoriesByExpense() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Object[]> results = categoryRepo.findCategoriesByAmount(user, OperationType.EXPENSE);
        List<CategoryAmount> categoryAmounts = new ArrayList<>();

        for (Object[] row : results) {
            Category category = (Category) row[0];
            Double totalAmount = (Double) row[1];
            categoryAmounts.add(new CategoryAmount(category, totalAmount));
        }
        return categoryAmounts;
    }

    public List<MonthAmount> findMonthsByProfit() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Object[]> results = operationRepo.findMonthsByAmount(user, OperationType.PROFIT);
        List<MonthAmount> monthAmounts = new ArrayList<>();

        for (Object[] row : results) {
            int year = (int) row[0];
            int month = (int) row[1];
            double total = (double) row[2];
            monthAmounts.add(new MonthAmount(year, month, total));
        }

        return monthAmounts;
    }

    public List<MonthAmount> findMonthsByExpense() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Object[]> results = operationRepo.findMonthsByAmount(user, OperationType.EXPENSE);
        List<MonthAmount> monthAmounts = new ArrayList<>();

        for (Object[] row : results) {
            int year = (int) row[0];
            int month = (int) row[1];
            double total = (double) row[2];
            monthAmounts.add(new MonthAmount(year, month, total));
        }

        return monthAmounts;
    }


}
