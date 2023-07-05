package com.hitzseb.walletwizardapi.responses;

import com.hitzseb.walletwizardapi.dto.CategoryAmount;
import com.hitzseb.walletwizardapi.dto.MonthAmount;

import java.util.List;

public record ReportsResponse(List<CategoryAmount> categoriesByProfit,
                              List<CategoryAmount> categoriesByExpense,
                              List<MonthAmount> monthsByProfit,
                              List<MonthAmount> monthsByExpense) {
}
