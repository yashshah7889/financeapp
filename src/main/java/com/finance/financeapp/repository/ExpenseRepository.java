package com.finance.financeapp.repository;

import com.finance.financeapp.model.Expense;
import com.finance.financeapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * This repository provides methods to find expenses by user, date range, and category
 */
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
    List<Expense> findByUserAndCategory(User user, String category);

    List<Expense> findByUser(User user);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user = :user AND e.category = :category AND e.date BETWEEN :startDate AND :endDate")
    BigDecimal sumByUserAndCategoryAndDateRange(@Param("user") User user,
                                                @Param("category") String category,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);
}

