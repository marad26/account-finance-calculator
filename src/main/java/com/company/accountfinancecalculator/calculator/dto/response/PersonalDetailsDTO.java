package com.company.accountfinancecalculator.calculator.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PersonalDetailsDTO {

    String name;
    String surname;
}
