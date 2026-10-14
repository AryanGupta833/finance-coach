package com.financecoach.model;

import java.time.YearMonth;

public record MonthlyReport(
    YearMonth month,
    double income,
    double needs,
    double wants,
    double savings,
    double debt,
    double other,
    Double needsPercent,
    Double wantsPercent,
    Double savingsDebtPercent
) {
}