package com.finance.financeapp.repository;

import com.finance.financeapp.model.Income;
import com.finance.financeapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

/**
 * This repository provides methods to find incomes by user and date range.
 */
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);

    List<Income> findByUser(User user);
}
