package com.company.accountfinancecalculator.calculator.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Clients {

    List<Client> clients;
}
