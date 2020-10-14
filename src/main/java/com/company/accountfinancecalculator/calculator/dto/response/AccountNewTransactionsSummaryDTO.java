package com.company.accountfinancecalculator.calculator.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountNewTransactionsSummaryDTO {

    String currentBalance;
    String accountTurnover;
    String totalIncome;
    String totalExpenses;
}
