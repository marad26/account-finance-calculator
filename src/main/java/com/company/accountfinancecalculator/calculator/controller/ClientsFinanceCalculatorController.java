package com.company.accountfinancecalculator.calculator.controller;

import com.company.accountfinancecalculator.calculator.converter.ClientsRequestConverter;
import com.company.accountfinancecalculator.calculator.dto.request.*;
import com.company.accountfinancecalculator.calculator.dto.response.ClientNewTransactionsSummaryDTO;
import com.company.accountfinancecalculator.calculator.dto.response.ClientsNewTransactionsSummaryResponseDTO;
import com.company.accountfinancecalculator.calculator.converter.CalculatedClientNewTransactionsSummaryConverter;
import com.company.accountfinancecalculator.calculator.model.Clients;
import com.company.accountfinancecalculator.calculator.service.ClientNewTransactionsSummaryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/calculators")
public class ClientsFinanceCalculatorController {

    ClientsRequestConverter clientsRequestConverter;

    ClientNewTransactionsSummaryService clientNewTransactionsSummaryService;

    CalculatedClientNewTransactionsSummaryConverter calculatedClientNewTransactionsSummaryConverter;

    @ApiOperation(value = "Calculate new transactions summary for given client, between last account balance update date and current day",
            response = ClientsNewTransactionsSummaryResponseDTO.class)
    @ApiResponse(code = 400, message = "Wrong parameter value provided")
    @PostMapping("/newTransactionsSummary")
    public ResponseEntity<ClientsNewTransactionsSummaryResponseDTO> newTransactionsSummary(
            @RequestBody @Valid ClientsTransactionsRequestDTO request) {

        Clients clients = clientsRequestConverter.convertClientsRequest(request);

        List<ClientNewTransactionsSummaryDTO> calculatedClientsFinances = clients.getClients().stream()
                .map(client -> calculatedClientNewTransactionsSummaryConverter.createCalculatedClientFinance(
                        clientNewTransactionsSummaryService.calculateClientNewOperationsSummary(client), client.getPersonalDetails()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ClientsNewTransactionsSummaryResponseDTO.builder()
                .clients(calculatedClientsFinances)
                .build());
    }
}
