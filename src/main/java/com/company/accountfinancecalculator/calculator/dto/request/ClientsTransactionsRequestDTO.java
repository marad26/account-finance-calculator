package com.company.accountfinancecalculator.calculator.dto.request;

import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Value
@Builder
public class ClientsTransactionsRequestDTO {

    @Valid
    @NotNull
    ClientsDTO clients;
}
