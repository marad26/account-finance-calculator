package com.company.accountfinancecalculator.calculator.dto.request;

import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Value
@Builder
public class ClientsDTO {

    @Valid
    @NotNull
    @Size(min = 1)
    List<ClientDTO> client;
}
