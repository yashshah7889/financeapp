package com.finance.financeapp.controller;

import com.finance.financeapp.dto.ExpenseDTO;
import com.finance.financeapp.model.Expense;
import com.finance.financeapp.model.User;
import com.finance.financeapp.service.ExpenseService;
import com.finance.financeapp.service.UserService;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> addExpense(@Valid @RequestBody ExpenseDTO expenseDTO, BindingResult result, @RequestParam String username) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Expense expense = new Expense();
        expense.setUser(user);
        expense.setDescription(expenseDTO.getDescription());
        expense.setAmount(BigDecimal.valueOf(expenseDTO.getAmount()));
        expense.setDate(expenseDTO.getDate());
        expense.setCategory(expenseDTO.getCategory());

        Expense savedExpense = expenseService.addExpense(expense);
        return ResponseEntity.ok(savedExpense);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getUserExpenses(
            @RequestParam String username,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Expense> expenses = expenseService.getUserExpenses(user, startDate, endDate);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Expense>> getUserExpenses(@RequestParam String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Expense> expenses = expenseService.getUserExpenses(user);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Expense>> getUserExpensesByCategory(@RequestParam String username, @RequestParam String category) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Expense> expenses = expenseService.getUserExpensesByCategory(user, category);
        return ResponseEntity.ok(expenses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}

