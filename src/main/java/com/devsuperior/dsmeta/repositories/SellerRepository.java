package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.entities.Seller;
import com.devsuperior.dsmeta.projections.SellerMinProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query(nativeQuery = true, value = "SELECT tb_seller.name, SUM(tb_sales.amount) AS total_sales_amount " +
            "FROM tb_seller " +
            "INNER JOIN tb_sales ON tb_seller.id = tb_sales.seller_id " +
            "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY tb_seller.name ")
    Page<SellerMinProjection> searchSellerByInitialDateFinalDate
            (LocalDate minDate, LocalDate maxDate, Pageable pageable);
}


/* CONSULTA NO PGADMIN 4
SELECT tb_seller.name, SUM(tb_sales.amount) AS total_sales_amount
FROM tb_seller
INNER JOIN tb_sales ON tb_seller.id = tb_sales.seller_id
WHERE tb_sales.date BETWEEN '2022-09-16' AND '2023-09-16'
GROUP BY tb_seller.name
 */
