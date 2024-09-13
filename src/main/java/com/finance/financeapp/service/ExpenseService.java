package com.finance.financeapp.service;


import com.finance.financeapp.model.Budget;
import com.finance.financeapp.model.Expense;
import com.finance.financeapp.model.User;
import com.finance.financeapp.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * service provides methods to add, retrieve, and delete expenses, as well as retrieve expenses by date range and category.
 */
@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private BudgetService budgetService;

    public Expense addExpense(Expense expense) {
        User user = expense.getUser();
        List<Budget> budgets=  budgetService.getUserBudgetsByCategory(user, expense.getCategory());

        BigDecimal totalExpenses = expenseRepository.sumByUserAndCategoryAndDateRange(user, expense.getCategory(),
                expense.getDate(), expense.getDate().withDayOfMonth(1).plusMonths(1).minusDays(1));

        for (Budget budget : budgets) {
            if (totalExpenses.add(expense.getAmount()).compareTo(budget.getAmount()) > 0) {
                // Notify user they've exceeded the budget
                System.out.println("Warning: You've exceeded your budget for the " + budget.getCategory() + " category.");
            }
        }

        return expenseRepository.save(expense);


    }

    public List<Expense> getUserExpenses(User user, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByUserAndDateBetween(user, startDate, endDate);
    }

    public List<Expense> getUserExpensesByCategory(User user, String category) {
        return expenseRepository.findByUserAndCategory(user, category);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public List<Expense> getUserExpenses(User user) {
        return expenseRepository.findByUser(user);
    }
}

