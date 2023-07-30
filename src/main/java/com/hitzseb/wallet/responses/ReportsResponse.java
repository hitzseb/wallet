package com.hitzseb.wallet.responses;

import com.hitzseb.wallet.dto.CategoryAmount;
import com.hitzseb.wallet.dto.MonthAmount;

import java.util.List;

public record ReportsResponse(List<CategoryAmount> categoriesByProfit,
                              List<CategoryAmount> categoriesByExpense,
                              List<MonthAmount> monthsByProfit,
                              List<MonthAmount> monthsByExpense) {
}
