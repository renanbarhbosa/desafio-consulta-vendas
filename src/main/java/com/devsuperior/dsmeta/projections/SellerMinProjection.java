package com.devsuperior.dsmeta.projections;

import com.devsuperior.dsmeta.entities.Sale;

import java.util.List;

public interface SellerMinProjection {

    String getName();

    List<Sale> getSales();
}
