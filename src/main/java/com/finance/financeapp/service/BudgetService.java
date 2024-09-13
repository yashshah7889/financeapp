package com.finance.financeapp.service;


import com.finance.financeapp.model.Budget;
import com.finance.financeapp.model.User;
import com.finance.financeapp.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserService userService;

    public Budget addBudget(User user, String category, BigDecimal amount, LocalDate startDate, LocalDate endDate) {
        Budget budget = new Budget();
        budget.setUser(user);
        budget.setCategory(category);
        budget.setAmount(amount);
        budget.setStartDate(startDate);
        budget.setEndDate(endDate);
        return budgetRepository.save(budget);
    }

    public Budget createRecurringBudget(User user, String category, BigDecimal amount) {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        return addBudget(user, category, amount, startDate, endDate);
    }

    // Creates a recurring budget for a given number of months
    public void createRecurringBudget(User user, String category, BigDecimal amount, int months) {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);

        for (int i = 0; i < months; i++) {
            LocalDate currentStartDate = startDate.plusMonths(i);
            LocalDate currentEndDate = currentStartDate.plusMonths(1).minusDays(1);
            addBudget(user, category, amount, currentStartDate, currentEndDate);
        }
    }

    public List<Budget> getUserBudgets(User user) {
        return budgetRepository.findByUser(user);
    }

    public List<Budget> getUserBudgetsByCategory(User user, String category) {
        return budgetRepository.findByUserAndCategory(user, category);
    }
    public List<Budget> getUserRecurringBudgets(User user) {
        return budgetRepository.findByUserAndIsRecurring(user, true);  // Only return recurring budgets
    }
    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }

    @Scheduled(cron = "0 0 0 1 * ?") // Runs on the 1st of every month at midnight
    public void createMonthlyBudgets() {
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            List<Budget> recurringBudgets =getUserRecurringBudgets(user);
            for (Budget budget : recurringBudgets) {
                createRecurringBudget(user, budget.getCategory(), budget.getAmount());
            }
        }
    }

}