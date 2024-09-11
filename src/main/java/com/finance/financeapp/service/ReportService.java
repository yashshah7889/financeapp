package com.finance.financeapp.service;



import com.finance.financeapp.model.User;
import java.math.BigDecimal;
import java.util.Map;

public interface ReportService {
    Map<String, BigDecimal> getExpenseSummary(User user);
    Map<String, BigDecimal> getIncomeSummary(User user);
    BigDecimal getNetSavings(User user);
}
