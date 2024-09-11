package com.finance.financeapp.repository;


import com.finance.financeapp.model.Budget;
import com.finance.financeapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUser(User user);
    List<Budget> findByUserAndCategory(User user, String category);
}
