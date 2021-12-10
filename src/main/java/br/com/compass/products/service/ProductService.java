package br.com.compass.products.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.compass.products.controller.dto.ProductDto;
import br.com.compass.products.controller.form.ProductForm;

public interface ProductService {

	Page<ProductDto> list(Pageable pageable);
	
	ProductDto findById(Integer id);
	
	Page<ProductDto> productsSearch(Pageable pageable,
			Double maxPrice, Double minPrice, String q);
	
	ResponseEntity<ProductDto> create(ProductForm form, UriComponentsBuilder uriBuilder);

	ResponseEntity<ProductDto> update(Integer id, ProductForm form);

	ResponseEntity<?> delete(Integer id);
}
