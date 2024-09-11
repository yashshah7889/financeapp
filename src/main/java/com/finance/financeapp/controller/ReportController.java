package com.finance.financeapp.controller;

import com.finance.financeapp.model.User;
import com.finance.financeapp.service.ReportService;
import com.finance.financeapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * This controller provides RESTful endpoints to retrieve expense summaries, income summaries, and net savings for a user.
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @GetMapping("/expenses")
    public ResponseEntity<Map<String, BigDecimal>> getExpenseSummary(@RequestParam String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Map<String, BigDecimal> expenseSummary = reportService.getExpenseSummary(user);
        return ResponseEntity.ok(expenseSummary);
    }

    @GetMapping("/incomes")
    public ResponseEntity<Map<String, BigDecimal>> getIncomeSummary(@RequestParam String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Map<String, BigDecimal> incomeSummary = reportService.getIncomeSummary(user);
        return ResponseEntity.ok(incomeSummary);
    }

    @GetMapping("/savings")
    public ResponseEntity<BigDecimal> getNetSavings(@RequestParam String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        BigDecimal netSavings = reportService.getNetSavings(user);
        return ResponseEntity.ok(netSavings);
    }
}
