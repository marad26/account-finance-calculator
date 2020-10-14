package com.company.accountfinancecalculator

import com.company.accountfinancecalculator.calculator.dto.request.AccountBalanceDTO
import com.company.accountfinancecalculator.calculator.dto.request.TransactionDTO
import com.company.accountfinancecalculator.calculator.dto.request.TransactionTypeDTO
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.ValidatorFactory
import javax.validation.Validator
import java.time.LocalDate

class ClientTransactionsRequestValidationsTest extends Specification {

    private Validator validator

    def setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory()
        validator = factory.getValidator()
    }

    @Unroll
    def "should allow only xxx.xx format in account balance, got #accountValue"() {
        when:
        AccountBalanceDTO accountBalance = AccountBalanceDTO.builder()
                .currency("PLN")
                .date(LocalDate.of(2012, 11, 12))
                .value(accountValue)
                .build()
        Set<ConstraintViolation<AccountBalanceDTO>> violations = validator.validate(accountBalance)

        then:
        violations.isEmpty() == result

        where:
        accountValue      | result
        "abc"             | false
        "123.12"          | false
        "123,12"          | true
        "123123123,12"    | true
        "123123123,121"   | false
        "123.123.123,12"  | false
        "123,123,123,123" | false
    }

    @Unroll
    def "should allow only xxx.xx format as transaction value, got #transactionValue"() {
        when:
        TransactionDTO transaction = TransactionDTO.builder()
                .date(LocalDate.of(2012, 11, 12))
                .currency("PLN")
                .value(transactionValue)
                .type(TransactionTypeDTO.INCOME)
                .build()
        Set<ConstraintViolation<TransactionDTO>> violations = validator.validate(transaction)

        then:
        violations.isEmpty() == result

        where:
        transactionValue  | result
        "abc"             | false
        "123.12"          | false
        "123,12"          | true
        "123123123,12"    | true
        "123123123,121"   | false
        "123.123.123,12"  | false
        "123,123,123,123" | false
    }
}
