package com.company.accountfinancecalculator

import com.company.accountfinancecalculator.calculator.model.AccountBalance
import com.company.accountfinancecalculator.calculator.model.Client
import com.company.accountfinancecalculator.calculator.model.PersonalDetails
import com.company.accountfinancecalculator.calculator.model.Transaction
import com.company.accountfinancecalculator.calculator.model.TransactionType
import com.company.accountfinancecalculator.calculator.service.CalculationsService
import com.company.accountfinancecalculator.calculator.service.ClientNewTransactionsSummaryService
import com.company.accountfinancecalculator.calculator.service.OperationTypesToCalculate
import org.javamoney.moneta.Money
import spock.lang.Specification

import java.time.LocalDate

class NewOperationsSummaryCalculationTest extends Specification {

    def "should calculate new operations summary"() {
        given:
        CalculationsService calculationsService = new CalculationsService()
        ClientNewTransactionsSummaryService clientTransactionsSummaryService = new ClientNewTransactionsSummaryService(calculationsService)
        Client client = createClient()

        when:
        def result = clientTransactionsSummaryService.calculateClientNewOperationsSummary(client)

        then: "should calculate total expenses from last time balance was saved until today"
        result.get(OperationTypesToCalculate.TOTAL_EXPENSES).isEqualTo(client.getTransactions().get(0).getValue())

        and: "should calculate turnover from last time balance was saved until today"
        result.get(OperationTypesToCalculate.ACCOUNT_TURNOVER).isEqualTo(client.getTransactions().get(1).getValue())

        and: "should calculate total income from last time balance was saved until today"
        def income = client.getTransactions().get(1).getValue().subtract(client.getTransactions().get(0).getValue())
        result.get(OperationTypesToCalculate.TOTAL_INCOME).isEqualTo(income)

        and: "should calculate new account balance given last time balance was saved until today"
        result.get(OperationTypesToCalculate.CURRENT_BALANCE).isEqualTo(client.getAccountBalance().getBalance().add(income))
    }

    def createClient() {
        return Client.builder()
                .accountBalance(AccountBalance.builder()
                        .date(LocalDate.of(2012, 11, 12))
                        .balance(Money.of(123.55, "PLN"))
                        .build())
                .personalDetails(PersonalDetails.builder()
                        .name("adam")
                        .surname("mada")
                        .build())
                .transactions(Arrays.asList(
                        Transaction.builder()
                                .transactionType(TransactionType.OUTCOME)
                                .description("przelew środków")
                                .date(LocalDate.of(2012, 12, 12))
                                .value(Money.of(12, "PLN"))
                                .build(),
                        Transaction.builder()
                                .transactionType(TransactionType.INCOME)
                                .description("transakcja przychodząca")
                                .date(LocalDate.of(2013, 12, 12))
                                .value(Money.of(122, "PLN"))
                                .build()))
                .build()
    }
}
