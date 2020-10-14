package com.company.accountfinancecalculator.calculator.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TransactionTypeDTO {

    INCOME("income"),
    OUTCOME("outcome");

    private String type;

    private TransactionTypeDTO(String type) {
        this.type = type;
    }

    @JsonCreator
    public static TransactionTypeDTO forValue(String value) {
        for (TransactionTypeDTO transactionTypeDTO : TransactionTypeDTO.values()) {
            if (transactionTypeDTO.type.equals(value.toLowerCase())) {
                return transactionTypeDTO;
            }
        }
        throw new IllegalArgumentException("Wrong transaction type provided");
    }
}
