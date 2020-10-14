package com.company.accountfinancecalculator.calculator.dto.response;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ClientsNewTransactionsSummaryResponseDTO {

    List<ClientNewTransactionsSummaryDTO> clients;
}
