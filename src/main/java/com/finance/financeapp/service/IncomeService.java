package com.finance.financeapp.service;

import com.finance.financeapp.model.Income;
import com.finance.financeapp.model.User;
import com.finance.financeapp.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * This service provides methods to add, retrieve, and delete incomes, as well as retrieve incomes by date range.
 */
@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    public Income addIncome(Income income) {
        return incomeRepository.save(income);
    }

    public List<Income> getUserIncomes(User user, LocalDate startDate, LocalDate endDate) {
        return incomeRepository.findByUserAndDateBetween(user, startDate, endDate);
    }

    public void deleteIncome(Long id) {
        incomeRepository.deleteById(id);
    }
}
