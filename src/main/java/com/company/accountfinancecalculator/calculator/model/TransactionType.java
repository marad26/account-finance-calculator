package com.company.accountfinancecalculator.calculator.model;

public enum TransactionType {

    INCOME("income"),
    OUTCOME("outcome");

    private String type;

    private TransactionType(String type) {
        this.type = type;
    }
}
