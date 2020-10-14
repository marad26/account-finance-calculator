package com.company.accountfinancecalculator.calculator.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonalDetails {

    String name;
    String surname;
}
