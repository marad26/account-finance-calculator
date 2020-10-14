package com.company.accountfinancecalculator.calculator.converter;

import javax.money.MonetaryAmount;

public interface MoneyConverter {

    MonetaryAmount convert(String value, String currency);
}
