package com.finance.financeapp.repository;

import com.finance.financeapp.model.Expense;
import com.finance.financeapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

/**
 * This repository provides methods to find expenses by user, date range, and category
 */
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
    List<Expense> findByUserAndCategory(User user, String category);
}

