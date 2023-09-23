package com.devsuperior.dsmeta.dtos;

import com.devsuperior.dsmeta.entities.Sale;

import java.time.LocalDate;

public class SaleSummaryDTO {

    private String name;
    private Double amount;


    public SaleSummaryDTO() {
    }

    public SaleSummaryDTO(String name, Double amount) {
        this.name = name;
        this.amount = amount;
    }

    public SaleSummaryDTO(Sale entity) {
        name = entity.getSeller().getName();
        amount = entity.getAmount();
    }

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return amount;
    }
}
