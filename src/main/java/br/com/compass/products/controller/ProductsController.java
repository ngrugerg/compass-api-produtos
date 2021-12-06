package br.com.compass.products.controller;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.compass.products.controller.dto.ProductDto;
import br.com.compass.products.controller.form.ProductForm;
import br.com.compass.products.model.Product;
import br.com.compass.products.repository.ProductsRepository;

@RestController
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	private ProductsRepository productsRepository;

	@GetMapping
	public Page<ProductDto> list(@RequestParam(required = false) String name,
			@PageableDefault(sort = "name", direction = Direction.ASC, page = 0, size = 10) Pageable pageable) {
		Page<Product> products = productsRepository.findAll(pageable);
		return ProductDto.convert(products);

	}

	@GetMapping("/search")
	public Page<ProductDto> productsSearch(
			@PageableDefault(sort = "name", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable,
			@RequestParam(required = false) Double maxPrice, @RequestParam(required = false) Double minPrice,
			@RequestParam(required = false) String q) {

		Page<Product> products = productsRepository.findBySearch(pageable, q, minPrice, maxPrice);
		return ProductDto.convert(products);
	}

	@GetMapping("/{id}")
	public ProductDto findById(@PathVariable Integer id) {
		Product product = productsRepository.getById(id);
		return new ProductDto(product);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<ProductDto> create(@RequestBody @Valid ProductForm form, UriComponentsBuilder uriBuilder) {
		Product product = form.converter(productsRepository);
		productsRepository.save(product);

		URI uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
		return ResponseEntity.created(uri).body(new ProductDto(product));
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ProductDto> update(@PathVariable Integer id, @RequestBody @Valid ProductForm form) {
		Product product = form.update(id, productsRepository);
		return ResponseEntity.ok(new ProductDto(product));
	}

	@DeleteMapping("{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		productsRepository.deleteById(id);
		return ResponseEntity.ok().build();

	}

}
