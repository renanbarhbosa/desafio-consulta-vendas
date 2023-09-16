package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import com.devsuperior.dsmeta.dtos.SellerMinDTO;
import com.devsuperior.dsmeta.entities.Seller;
import com.devsuperior.dsmeta.projections.SellerMinProjection;
import com.devsuperior.dsmeta.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dtos.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SellerRepository sellerRepository;

    final LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

    public SaleMinDTO findById(Long id) {
        Optional<Sale> result = saleRepository.findById(id);
        Sale entity = result.get();
        return new SaleMinDTO(entity);
    }

    public Page<SellerMinDTO> searchPagedSellerWithInitialDateFinalDate
            (String minDateStr, String maxDateStr, Pageable pageable) {

        LocalDate minDate;
        LocalDate maxDate;

        try {
            if (maxDateStr != null && !maxDateStr.isEmpty()) {
                maxDate = LocalDate.parse(maxDateStr);
            } else {
                maxDate = LocalDate.now();
            }

            if (minDateStr != null && !minDateStr.isEmpty()) {
                minDate = LocalDate.parse(minDateStr);
            } else {
                minDate = maxDate.minusYears(1L);
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de data inválido");
        }

        Page<SellerMinProjection> saleMinProjectionPage = sellerRepository
                .searchSellerByInitialDateFinalDate(minDate, maxDate, pageable);
        Page<SellerMinDTO> res = saleMinProjectionPage.map(x -> new SellerMinDTO(x));
        return res;

    }

    public Page<SaleMinDTO> searchPagedSalesWithInitialDateFinalDateAndSellerPartialName
            (String minDateStr, String maxDateStr, String name, Pageable pageable) {

        LocalDate minDate;
        LocalDate maxDate;

        try {

            if (maxDateStr != null && !maxDateStr.isEmpty()) {
                maxDate = LocalDate.parse(maxDateStr);
            } else {
                maxDate = LocalDate.now();
            }

            if (minDateStr != null && !minDateStr.isEmpty()) {
                minDate = LocalDate.parse(minDateStr);
            } else {
                minDate = maxDate.minusYears(1L);
            }

            if (name == null) {
                name = "";
            }

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de data inválido");
        }

        Page<Sale> saleMinProjectionPage = saleRepository
                .searchSaleByInitialDateFinalDateAndSellerPartialName(minDate, maxDate, name, pageable);
        Page<SaleMinDTO> res = saleMinProjectionPage.map(x -> new SaleMinDTO(x));
        return res;
    }
}
