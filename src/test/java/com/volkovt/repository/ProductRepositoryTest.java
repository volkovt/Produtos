package com.volkovt.repository;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.volkovt.administrativo.entity.Product;
import com.volkovt.administrativo.repository.ProductRepository;

@DataJpaTest
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository repository;
	
	@Test
	public void findProductsShouldReturnProductsWhenValidData() {
		BigDecimal min_price = BigDecimal.valueOf(3.40);
		BigDecimal max_price = BigDecimal.valueOf(7.39);
		String term = "%ACHOCO%";
		Integer count = 2;
		List<Product> result = repository.findProducts(min_price, max_price, term);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(count, result.size());
	}
}
