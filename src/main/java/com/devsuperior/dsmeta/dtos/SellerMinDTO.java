package com.devsuperior.dsmeta.dtos;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.entities.Seller;
import com.devsuperior.dsmeta.projections.SellerMinProjection;

import java.util.ArrayList;
import java.util.List;

public class SellerMinDTO {

    private String name;
    private List<Sale> sales = new ArrayList<>();

    public SellerMinDTO() {
    }

    public SellerMinDTO(String name, List<Sale> sales) {
        this.name = name;
        this.sales = sales;
    }

    public SellerMinDTO(SellerMinProjection projection) {
        name = projection.getName();
        sales = projection.getSales();
    }

    public SellerMinDTO(Seller entity) {
        name = entity.getName();
        sales = entity.getSales();
    }

    public String getName() {
        return name;
    }

    public List<Sale> getSales() {
        return sales;
    }
}
