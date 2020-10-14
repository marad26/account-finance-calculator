package com.company.accountfinancecalculator.calculator.converter;

import com.company.accountfinancecalculator.calculator.dto.response.AccountNewTransactionsSummaryDTO;
import com.company.accountfinancecalculator.calculator.dto.response.ClientNewTransactionsSummaryDTO;
import com.company.accountfinancecalculator.calculator.dto.response.PersonalDetailsDTO;
import com.company.accountfinancecalculator.calculator.model.PersonalDetails;
import com.company.accountfinancecalculator.calculator.service.OperationTypesToCalculate;
import lombok.AllArgsConstructor;
import org.javamoney.moneta.format.AmountFormatParams;

import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.HashMap;
import java.util.Locale;

@AllArgsConstructor
public class CalculatedClientNewTransactionsSummaryConverter {

    public ClientNewTransactionsSummaryDTO createCalculatedClientFinance(
            HashMap<OperationTypesToCalculate, MonetaryAmount> calculatedSummaryOperations, PersonalDetails personalDetails) {
        MonetaryAmountFormat formatter = createDefaultFormatter();

        return ClientNewTransactionsSummaryDTO.builder()
                .clientInfo(PersonalDetailsDTO.builder()
                        .name(personalDetails.getName())
                        .surname(personalDetails.getSurname())
                        .build())
                .calculatedAccountOperations(AccountNewTransactionsSummaryDTO.builder()
                        .totalExpenses(formatter.format(calculatedSummaryOperations.get(OperationTypesToCalculate.TOTAL_EXPENSES)))
                        .accountTurnover(formatter.format(calculatedSummaryOperations.get(OperationTypesToCalculate.ACCOUNT_TURNOVER)))
                        .totalIncome(formatter.format(calculatedSummaryOperations.get(OperationTypesToCalculate.TOTAL_INCOME)))
                        .currentBalance(formatter.format(calculatedSummaryOperations.get(OperationTypesToCalculate.CURRENT_BALANCE)))
                        .build())
                .build();
    }

    private MonetaryAmountFormat createDefaultFormatter() {
        return MonetaryFormats.getAmountFormat(
                AmountFormatQueryBuilder.of(Locale.getDefault())
                        .set(AmountFormatParams.GROUPING_SIZES, new int[]{2, 0})
                        .build());
    }
}
