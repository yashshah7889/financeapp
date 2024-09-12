package com.finance.financeapp.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class IncomeDTO {

    @NotBlank(message = "Source is required")
    @Size(min = 3, max = 255, message = "Source must be between 3 and 255 characters")
    private String source;

    @Positive(message = "Amount must be greater than 0")
    private double amount;

    @NotNull(message = "Date is required")
    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDate date;
}
