package com.volkovt.administrativo.controller;

import java.math.BigDecimal;
import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.volkovt.administrativo.entity.Product;
import com.volkovt.administrativo.service.ProductService;

import io.swagger.annotations.*;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService service;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(code = 200, message = "Produtos obtidos")
	@ApiOperation("Obter detalhes de todos produtos.")
	public List<Product> getProducts() {
		return service.getProducts();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses({ 
			@ApiResponse(code = 201, message = "Produto salvo"),
			@ApiResponse(code = 400, message = "Erro ao tentar salvar um produto") })
	@ApiOperation("Salvar um produto.")
	public Product saveProduct(@RequestBody @Valid @ApiParam(name = "product", value = "JSON do produto") Product product) {
		return service.saveProduct(product);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Produto atualizado"),
		@ApiResponse(code = 400, message = "Erro ao tentar atualizar um produto") })
	@ApiOperation("Atualizar um produto.")
	public Product updateProduct(@PathVariable @ApiParam(name = "id", value = "Id do cliente a ser atualizado. Não pode ser nulo") String id, 
			@RequestBody @Valid @ApiParam(name = "product", value = "JSON do produto") Product product) {
		return service.updateProduct(id, product);
	}

	@GetMapping("/{id}")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Produto encontrado"),
		@ApiResponse(code = 404, message = "Produto não pode ser localizado") })
	@ApiOperation("Obter detalhe de um único produto.")
	public ResponseEntity<Product> getProduct(@PathVariable @ApiParam(name = "id", value = "Id do cliente a ser atualizado. Não pode ser nulo") String id) {
		Product product = service.getProduct(id);
		return Objects.nonNull(product) ? ResponseEntity.ok(service.getProduct(id)) : ResponseEntity.notFound().build();
	}

	@GetMapping("/search")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(code = 200, message = "Produtos encontrados")
	@ApiOperation("Procurar por produtos.")
	public List<Product> findProducts(@RequestParam(required = false) @ApiParam(name = "min_price", value = "Valor mínimo que os produtos podem ter", example = "0.0") BigDecimal min_price,
			@RequestParam(required = false) @ApiParam(name = "max_price", value = "Valor máximo que os produtos podem ter", example = "0.0") BigDecimal max_price, 
			@RequestParam(required = false) @ApiParam(name = "q", value = "Termo usado para consultar a descrição e nome dos produtos (BASE64)") String q) {
		return service.findProducts(min_price, max_price, q);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation("Deletar um produto.")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Produto deletado com sucesso"),
		@ApiResponse(code = 404, message = "Produto não pode ser localizado") })
	public void deleteProduct(@PathVariable @ApiParam(name = "id", value = "Id do cliente a ser atualizado. Não pode ser nulo") String id) {
		service.deleteProduct(id);
	}
}
