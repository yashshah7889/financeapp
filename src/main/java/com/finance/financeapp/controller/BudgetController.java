package com.finance.financeapp.controller;
import com.finance.financeapp.model.Budget;
import com.finance.financeapp.model.User;
import com.finance.financeapp.service.BudgetService;
import com.finance.financeapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Budget> addBudget(@RequestBody Budget budget, @RequestParam String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        budget.setUser(user);
        Budget savedBudget = budgetService.addBudget(user, budget.getCategory(),
                budget.getAmount(),
                budget.getStartDate(),
                budget.getEndDate());
        return ResponseEntity.ok(savedBudget);
    }

    @GetMapping
    public ResponseEntity<List<Budget>> getUserBudgets(@RequestParam String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Budget> budgets = budgetService.getUserBudgets(user);
        return ResponseEntity.ok(budgets);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Budget>> getUserBudgetsByCategory(
            @RequestParam String username,
            @RequestParam String category) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Budget> budgets = budgetService.getUserBudgetsByCategory(user, category);
        return ResponseEntity.ok(budgets);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
}
