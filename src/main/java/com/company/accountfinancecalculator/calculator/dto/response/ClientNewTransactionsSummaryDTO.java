package com.company.accountfinancecalculator.calculator.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClientNewTransactionsSummaryDTO {

    PersonalDetailsDTO clientInfo;
    AccountNewTransactionsSummaryDTO calculatedAccountOperations;
}
