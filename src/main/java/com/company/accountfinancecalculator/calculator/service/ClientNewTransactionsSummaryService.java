package com.company.accountfinancecalculator.calculator.service;

import com.company.accountfinancecalculator.calculator.model.AccountBalance;
import com.company.accountfinancecalculator.calculator.model.Client;
import lombok.AllArgsConstructor;

import javax.money.MonetaryAmount;
import java.util.HashMap;

@AllArgsConstructor
public class ClientNewTransactionsSummaryService {

    CalculationsService calculationsService;

    public HashMap<OperationTypesToCalculate, MonetaryAmount> calculateClientNewOperationsSummary(Client client) {
        AccountBalance accountBalance = client.getAccountBalance();
        HashMap<OperationTypesToCalculate, MonetaryAmount> calculatedOperations = new HashMap<>();

        calculatedOperations.put(OperationTypesToCalculate.TOTAL_EXPENSES, calculationsService.calculateTotalExpenses(
                accountBalance.getDate(), client.getTransactions(), accountBalance.getBalance().getCurrency()));

        calculatedOperations.put(OperationTypesToCalculate.ACCOUNT_TURNOVER, calculationsService.calculateAccountTurnover(
                accountBalance.getDate(), client.getTransactions(), accountBalance.getBalance().getCurrency()));

        calculatedOperations.put(OperationTypesToCalculate.TOTAL_INCOME, calculationsService.calculateTotalIncome(
                calculatedOperations.get(OperationTypesToCalculate.TOTAL_EXPENSES),
                calculatedOperations.get(OperationTypesToCalculate.ACCOUNT_TURNOVER)));

        calculatedOperations.put(OperationTypesToCalculate.CURRENT_BALANCE, (calculationsService.calculateCurrentBalance(
                client.getAccountBalance().getBalance(), calculatedOperations.get(OperationTypesToCalculate.TOTAL_INCOME))));

        return calculatedOperations;
    }
}
