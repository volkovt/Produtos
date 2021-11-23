package com.volkovt.service;

import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.volkovt.administrativo.entity.Product;
import com.volkovt.administrativo.exception.RegraNegocioExcecao;
import com.volkovt.administrativo.repository.ProductRepository;
import com.volkovt.administrativo.service.ProductService;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {
	
	@InjectMocks
	private ProductService service;
	
	
	@Mock
	private ProductRepository repository;
	
	private Product product;
	
	private String existingId;
	
	private String nonExistingId;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = "1";
		nonExistingId = "2";
		product = new Product(existingId, "Nescau", "Achocolatado", BigDecimal.valueOf(7.59));
		
		Mockito.when(repository.save(any())).thenReturn(product);
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
	}
	
	@Test
	public void saveShouldReturnProductWhenValidData() {
		Product savedProduct = service.saveProduct(product);
		
		Assertions.assertNotNull(savedProduct);
	}
	
	@Test
	public void saveShouldThrowRegraNegocioExcecaoWhenPriceSmallerEqualZero() {
		Product invalidProduct = new Product("1", "Nescau", "Achocolatado", BigDecimal.ZERO);
		
		Assertions.assertThrows(RegraNegocioExcecao.class, () -> {
			service.saveProduct(invalidProduct);
		});
	}
	
	@Test
	public void updateShouldReturnProductWhenValidData() {
		Product updatedProduct = service.updateProduct(existingId, product);
		
		Assertions.assertNotNull(updatedProduct);
		Assertions.assertEquals(existingId, updatedProduct.getId());
	}

	@Test
	public void updateShouldThrowRegraNegocioExcecaoWhenInvalidId() {
		Assertions.assertThrows(RegraNegocioExcecao.class, () -> {
			service.updateProduct(nonExistingId, product);
		});
	}
	
	@Test
	public void deleteShouldThrowRegraNegocioExcecaoWhenInvalidId() {
		Assertions.assertThrows(RegraNegocioExcecao.class, () -> {
			service.deleteProduct(nonExistingId);
		});
	}
}
