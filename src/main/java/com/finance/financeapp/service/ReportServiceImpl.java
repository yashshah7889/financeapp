package com.finance.financeapp.service;

import com.finance.financeapp.model.Expense;
import com.finance.financeapp.model.Income;
import com.finance.financeapp.model.User;
import com.finance.financeapp.repository.ExpenseRepository;
import com.finance.financeapp.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Override
    public Map<String, BigDecimal> getExpenseSummary(User user) {
        List<Expense> expenses = expenseRepository.findByUser(user);
        Map<String, BigDecimal> expenseSummary = new HashMap<>();
        for (Expense expense : expenses) {
            expenseSummary.merge(expense.getCategory(), expense.getAmount(), BigDecimal::add);
        }
        return expenseSummary;
    }

    @Override
    public Map<String, BigDecimal> getIncomeSummary(User user) {
        List<Income> incomes = incomeRepository.findByUser(user);
        Map<String, BigDecimal> incomeSummary = new HashMap<>();
        for (Income income : incomes) {
            incomeSummary.merge(income.getSource(), income.getAmount(), BigDecimal::add);
        }
        return incomeSummary;
    }

    @Override
    public BigDecimal getNetSavings(User user) {
        BigDecimal totalIncome = incomeRepository.findByUser(user).stream()
                .map(Income::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpenses = expenseRepository.findByUser(user).stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalIncome.subtract(totalExpenses);
    }
}
