package com.volkovt.administrativo.service;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.volkovt.administrativo.entity.Product;
import com.volkovt.administrativo.exception.RegraNegocioExcecao;
import com.volkovt.administrativo.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository repository;

	@Transactional(readOnly = true)
	public List<Product> getProducts() {
		return repository.findAll();
	}

	@Transactional
	public Product saveProduct(Product product) {
		validate(product, null);
		return repository.save(product);
	}

	@Transactional(readOnly = true)
	private void validate(Product product, String id) {
		if (Objects.nonNull(id) && repository.findById(id).isEmpty()) {
			throw new RegraNegocioExcecao(HttpStatus.NOT_FOUND, "Product not found");
		}

		if (Objects.nonNull(product) && product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new RegraNegocioExcecao(HttpStatus.BAD_REQUEST, "Price needs to be bigger than zero");
		}
	}

	@Transactional
	public Product updateProduct(String id, Product product) {
		validate(product, id);
		product.setId(id);
		return repository.save(product);
	}

	@Transactional(readOnly = true)
	public Product getProduct(String id) {
		return repository.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public List<Product> findProducts(BigDecimal min_price, BigDecimal max_price, String q) {
		String term = null;
		if (Objects.nonNull(q)) {
			term = new String(Base64.getDecoder().decode(q)).toUpperCase();
			term = "%" + term + "%";
		}

		return repository.findProducts(min_price, max_price, term);
	}

	@Transactional
	public void deleteProduct(String id) {
		validate(null, id);
		repository.deleteById(id);
	}
}
