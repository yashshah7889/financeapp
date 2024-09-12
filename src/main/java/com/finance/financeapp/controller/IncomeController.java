package com.finance.financeapp.controller;

import com.finance.financeapp.model.Income;
import com.finance.financeapp.model.User;
import com.finance.financeapp.service.IncomeService;
import com.finance.financeapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 *This controller provides RESTful endpoints to add, retrieve, and delete incomes. It handles requests for retrieving incomes by date range.
 */
@RestController
@RequestMapping("/api/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Income> addIncome(@RequestBody Income income, @RequestParam String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        income.setUser(user);
        Income savedIncome = incomeService.addIncome(income);
        return ResponseEntity.ok(savedIncome);
    }

    @GetMapping
    public ResponseEntity<List<Income>> getUserIncomes(
            @RequestParam String username,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Income> incomes = incomeService.getUserIncomes(user, startDate, endDate);
        return ResponseEntity.ok(incomes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }
}

