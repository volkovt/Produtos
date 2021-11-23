package com.volkovt.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.volkovt.administrativo.entity.Product;
import com.volkovt.administrativo.exception.RegraNegocioExcecao;
import com.volkovt.administrativo.service.ProductService;

@WebMvcTest
public class ProductControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	ProductService service;
	
	
	private String existingId;
	private String nonExistingId;
	
	private Product product;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = "1";
		nonExistingId = "2";
		
		product = new Product(existingId, "Nescau", "Achocolatado", BigDecimal.valueOf(7.59));
		
		Mockito.when(service.getProducts()).thenReturn(List.of(product));
		Mockito.when(service.saveProduct(any())).thenReturn(product);
		Mockito.when(service.updateProduct(eq(existingId), any())).thenReturn(product);
		Mockito.when(service.updateProduct(eq(nonExistingId), any())).thenThrow(RegraNegocioExcecao.class);
		Mockito.doNothing().when(service).deleteProduct(existingId);
		doThrow(RegraNegocioExcecao.class).when(service).deleteProduct(nonExistingId);
	}
	
	
	@Test
	void getProductsShouldReturnAllProducts() throws Exception {
		mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	void saveProductsShouldReturnProductCreated() throws Exception {
		String json = objectMapper.writeValueAsString(product);
		
		mockMvc.perform(post("/products")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}
	
	@Test
	void updateShouldReturnProductWhenIdIsValid() throws Exception {
		String json = objectMapper.writeValueAsString(product);
		mockMvc.perform(put("/products/{id}", existingId)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	@Test
	void deleteShouldReturnNoContentWhenIdValid() throws Exception {
		mockMvc.perform(delete("/products/{id}", existingId)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
}
