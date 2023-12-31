package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dtos.SaleSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query(value = """
            SELECT obj FROM Sale obj JOIN FETCH obj.seller
            WHERE obj.date >= :min
            AND obj.date <= :max
            AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))
            """, countQuery = """
            SELECT COUNT(obj) FROM Sale obj JOIN obj.seller
            WHERE obj.date >= :min
            AND obj.date <= :max
            AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))
            """)
    Page<Sale> searchSaleByInitialDateFinalDateAndSellerPartialName
            (LocalDate min, LocalDate max, String name, Pageable pageable);

    @Query(value = """
        SELECT new com.devsuperior.dsmeta.dtos.SaleSummaryDTO(obj.seller.name, SUM(obj.amount))
        FROM Sale obj
        WHERE obj.date >= :min
        AND obj.date <= :max
        GROUP BY obj.seller.name
        """)
    List<SaleSummaryDTO> searchSalesBySeller(@Param("min") LocalDate min, @Param("max") LocalDate max);

}
