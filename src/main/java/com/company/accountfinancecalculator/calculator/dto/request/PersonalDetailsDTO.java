package com.company.accountfinancecalculator.calculator.dto.request;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder
public class PersonalDetailsDTO {

    @NotNull
    String name;

    @NotNull
    String surname;
}
