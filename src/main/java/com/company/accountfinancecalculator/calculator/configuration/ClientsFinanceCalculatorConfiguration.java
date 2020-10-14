package com.company.accountfinancecalculator.calculator.configuration;

import com.company.accountfinancecalculator.calculator.converter.CalculatedClientNewTransactionsSummaryConverter;
import com.company.accountfinancecalculator.calculator.converter.ClientsRequestConverter;
import com.company.accountfinancecalculator.calculator.converter.DefaultMoneyConverter;
import com.company.accountfinancecalculator.calculator.service.CalculationsService;
import com.company.accountfinancecalculator.calculator.service.ClientNewTransactionsSummaryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientsFinanceCalculatorConfiguration {

    @Bean
    CalculationsService calculationsService() {
        return new CalculationsService();
    }

    @Bean
    CalculatedClientNewTransactionsSummaryConverter calculatedClientFinanceConverter() {
        return new CalculatedClientNewTransactionsSummaryConverter();
    }

    @Bean
    ClientsRequestConverter clientsRequestConverter() {
        return new ClientsRequestConverter(new DefaultMoneyConverter());
    }

    @Bean
    ClientNewTransactionsSummaryService clientOperationsCalculationService(CalculationsService calculationsService) {
        return new ClientNewTransactionsSummaryService(calculationsService);
    }
}
