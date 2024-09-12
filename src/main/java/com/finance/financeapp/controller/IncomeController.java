package com.finance.financeapp.controller;

import com.finance.financeapp.dto.IncomeDTO;
import com.finance.financeapp.model.Income;
import com.finance.financeapp.model.User;
import com.finance.financeapp.service.IncomeService;
import com.finance.financeapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> addIncome(@Valid @RequestBody IncomeDTO incomeDTO, BindingResult result, @RequestParam String username) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Income income = new Income();
        income.setUser(user);
        income.setSource(incomeDTO.getSource());
        income.setAmount(BigDecimal.valueOf(incomeDTO.getAmount()));
        income.setDate(incomeDTO.getDate());

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


