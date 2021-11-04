package com.volkovt.administrativo.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.volkovt.administrativo.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>{

	@Query("FROM Product p "
			+ "WHERE (:min_price IS NULL OR p.price >= :min_price) "
			+ " AND (:max_price IS NULL OR p.price <= :max_price) "
			+ " AND (:term IS NULL "
			+ "		OR (UPPER(p.name) LIKE :term) "
			+ "		OR (UPPER(p.description) LIKE :term))")
	List<Product> findProducts(BigDecimal min_price, BigDecimal max_price, String term);
}
