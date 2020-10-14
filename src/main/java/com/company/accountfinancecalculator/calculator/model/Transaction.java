package com.company.accountfinancecalculator.calculator.model;

import lombok.Builder;
import lombok.Data;

import javax.money.MonetaryAmount;
import java.time.LocalDate;

@Data
@Builder
public class Transaction {

    TransactionType transactionType;
    String description;
    MonetaryAmount value;
    LocalDate date;
}
