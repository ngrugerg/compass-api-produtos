package br.com.compass.products.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.compass.products.controller.dto.ProductDto;
import br.com.compass.products.controller.form.ProductForm;
import br.com.compass.products.model.Product;
import br.com.compass.products.repository.ProductsRepository;

@Service
public class ProductServiceImp implements ProductService {

	@Autowired
	private ProductsRepository productsRepository;

	public Page<ProductDto> list(Pageable pageable) {
		Page<Product> products = productsRepository.findAll(pageable);
		return ProductDto.convert(products);
	}

	public ProductDto findById(Integer id) {
		Product product = productsRepository.getById(id);
		return new ProductDto(product);
	}

	@Override
	public Page<ProductDto> productsSearch(Pageable pageable, Double maxPrice, Double minPrice, String q) {
		Page<Product> products = productsRepository.findBySearch(pageable, q, minPrice, maxPrice);
		return ProductDto.convert(products);
	}

	@Override
	public ResponseEntity<ProductDto> create(ProductForm form, UriComponentsBuilder uriBuilder) {
		Product product = form.converter(productsRepository);
		productsRepository.save(product);

		URI uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
		return ResponseEntity.created(uri).body(new ProductDto(product));
	}

	@Override
	public ResponseEntity<ProductDto> update(Integer id, ProductForm form) {
		Product product = form.update(id, productsRepository);
		return ResponseEntity.ok(new ProductDto(product));
	}

	@Override
	public ResponseEntity<?> delete(Integer id) {
		productsRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
