package com.finance.financeapp.service;


import com.finance.financeapp.model.Budget;
import com.finance.financeapp.model.User;
import com.finance.financeapp.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    public Budget addBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    public List<Budget> getUserBudgets(User user) {
        return budgetRepository.findByUser(user);
    }

    public List<Budget> getUserBudgetsByCategory(User user, String category) {
        return budgetRepository.findByUserAndCategory(user, category);
    }

    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }
}
