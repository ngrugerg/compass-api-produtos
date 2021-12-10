package br.com.compass.products.controller;

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
import br.com.compass.products.repository.ProductsRepository;
import br.com.compass.products.service.ProductServiceImp;

@RestController
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	private ProductsRepository productsRepository;
	
	@Autowired
	private ProductServiceImp productService;

	@GetMapping
	public Page<ProductDto> list(@RequestParam(required = false) String name,
			@PageableDefault(sort = "name", direction = Direction.ASC, page = 0, size = 10) Pageable pageable) {
		return productService.list(pageable);
	}

	@GetMapping("/{id}")
	public ProductDto findById(@PathVariable Integer id) {
		return productService.findById(id);
	}

	@GetMapping("/search")
	public Page<ProductDto> productsSearch(
			@PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pageable,
			@RequestParam(required = false) Double maxPrice, @RequestParam(required = false) Double minPrice,
			@RequestParam(required = false) String q) {

		return productService.productsSearch(pageable, maxPrice, minPrice, q);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<ProductDto> create(@RequestBody @Valid ProductForm form, UriComponentsBuilder uriBuilder) {
		return productService.create(form, uriBuilder);
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ProductDto> update(@PathVariable Integer id, @RequestBody @Valid ProductForm form) {
		return productService.update(id, form);
	}

	@DeleteMapping("{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		return productService.delete(id);
	}

}
