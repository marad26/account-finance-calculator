package com.company.accountfinancecalculator.calculator.converter;

import com.company.accountfinancecalculator.calculator.dto.request.*;
import com.company.accountfinancecalculator.calculator.model.*;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ClientsRequestConverter {

    MoneyConverter moneyConverter;

    public Clients convertClientsRequest(ClientsTransactionsRequestDTO request) {
        return Clients.builder()
                .clients(convertClientsList(request.getClients().getClient()))
                .build();
    }

    private List<Client> convertClientsList(List<ClientDTO> clients) {
        return clients.stream()
                .map(client -> Client.builder()
                        .personalDetails(convertPersonalDetails(client.getInfo()))
                        .accountBalance(convertAccountBalance(client.getBalance()))
                        .transactions(convertTransactions(client.getTransactions()))
                        .build())
                .collect(Collectors.toList());
    }

    private PersonalDetails convertPersonalDetails(PersonalDetailsDTO personalDetails) {
        return PersonalDetails.builder()
                .name(personalDetails.getName())
                .surname(personalDetails.getSurname())
                .build();
    }

    private AccountBalance convertAccountBalance(AccountBalanceDTO accountBalance) {
        return AccountBalance.builder()
                .balance(moneyConverter.convert(accountBalance.getValue(), accountBalance.getCurrency()))
                .date(accountBalance.getDate())
                .build();
    }

    private List<Transaction> convertTransactions(List<TransactionDTO> transactions) {
        return transactions.stream()
                .map(transaction -> Transaction.builder()
                        .value(moneyConverter.convert(transaction.getValue(), transaction.getCurrency()))
                        .date(transaction.getDate())
                        .description(transaction.getDescription())
                        .transactionType(TransactionType.valueOf(transaction.getType().toString()))
                        .build())
                .collect(Collectors.toList());
    }
}
