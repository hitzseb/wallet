package com.hitzseb.billeterapp.response;

import com.hitzseb.billeterapp.dto.CategoryAmount;
import com.hitzseb.billeterapp.dto.MonthAmount;

import java.util.List;

public record ReportsResponse(List<CategoryAmount> categoriesByProfit,
                              List<CategoryAmount> categoriesByExpense,
                              List<MonthAmount> monthsByProfit,
                              List<MonthAmount> monthsByExpense) {
}
