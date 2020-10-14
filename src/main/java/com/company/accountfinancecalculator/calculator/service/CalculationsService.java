package com.company.accountfinancecalculator.calculator.service;

import com.company.accountfinancecalculator.calculator.model.Transaction;
import com.company.accountfinancecalculator.calculator.model.TransactionType;
import lombok.AllArgsConstructor;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryFunctions;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class CalculationsService {

    MonetaryAmount calculateCurrentBalance(MonetaryAmount balance, MonetaryAmount totalIncome) {
        return balance.add(totalIncome);
    }

    MonetaryAmount calculateTotalIncome(MonetaryAmount totalExpenses, MonetaryAmount turnover) {
        return turnover.subtract(totalExpenses);
    }

    MonetaryAmount calculateAccountTurnover(LocalDate dateFrom, List<Transaction> transactionsList,
                                            CurrencyUnit accountCurrency) {
        return calculateSumOfTransactions(dateFrom, TransactionType.INCOME, transactionsList)
                .orElse(Money.of(0, accountCurrency));
    }

    MonetaryAmount calculateTotalExpenses(LocalDate dateFrom, List<Transaction> transactionsList,
                                          CurrencyUnit accountCurrency) {
        return calculateSumOfTransactions(dateFrom, TransactionType.OUTCOME, transactionsList)
                .orElse(Money.of(0, accountCurrency));
    }

    private Optional<MonetaryAmount> calculateSumOfTransactions(LocalDate dateFrom, TransactionType transactionType,
                                                                List<Transaction> transactionsList) {
        return transactionsList.stream()
                .filter(transaction -> transaction.getDate().isAfter(dateFrom)
                        && transaction.getDate().isBefore(LocalDate.now().plusDays(1))
                        && transaction.getTransactionType().equals(transactionType))
                .map(Transaction::getValue)
                .reduce(MonetaryFunctions.sum());
    }
}
