package com.hitzseb.billeterapp.service;

import com.hitzseb.billeterapp.dto.CategoryAmount;
import com.hitzseb.billeterapp.dto.MonthAmount;
import com.hitzseb.billeterapp.model.Category;
import com.hitzseb.billeterapp.model.Transaction;
import com.hitzseb.billeterapp.model.User;
import com.hitzseb.billeterapp.repository.CategoryRepository;
import com.hitzseb.billeterapp.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportsService {

    private final CategoryRepository categoryRepository;
    private final OperationRepository operationRepository;

    public List<CategoryAmount> findCategoriesByProfit() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Object[]> results = categoryRepository.findCategoriesByAmount(user, Transaction.PROFIT);
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
        List<Object[]> results = categoryRepository.findCategoriesByAmount(user, Transaction.EXPENSE);
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
        List<Object[]> results = operationRepository.findMonthsByAmount(user, Transaction.PROFIT);
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
        List<Object[]> results = operationRepository.findMonthsByAmount(user, Transaction.EXPENSE);
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
