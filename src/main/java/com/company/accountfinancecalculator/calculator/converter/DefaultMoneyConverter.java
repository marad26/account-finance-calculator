package com.company.accountfinancecalculator.calculator.converter;

import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;

public class DefaultMoneyConverter implements MoneyConverter {

    public MonetaryAmount convert(String value, String currency) {
        value = value.replaceAll(",", ".");
        return Money.parse(String.join(" ", value, currency));
    }
}
