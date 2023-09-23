package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dtos.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dtos.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

    @Autowired
    private SaleService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
        SaleMinDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/report")
    public ResponseEntity<List<SaleSummaryDTO>> getReport(
            @RequestParam(name = "minDate", required = false) String minDate,
            @RequestParam(name = "maxDate", required = false) String maxDate) {

        List<SaleSummaryDTO> dto = service.
                searchPagedSellerWithInitialDateFinalDate
                        (minDate, maxDate);

        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/summary")
    public ResponseEntity<Page<SaleMinDTO>> getSummary(
            @RequestParam(name = "minDate", required = false) String minDate,
            @RequestParam(name = "maxDate", required = false) String maxDate,
            @RequestParam(name = "name", required = false) String name,
            Pageable pageable) {

        Page<SaleMinDTO> dto = service.
                searchPagedSalesWithInitialDateFinalDateAndSellerPartialName
                        (minDate, maxDate, name, pageable);

        return ResponseEntity.ok(dto);
    }
}
