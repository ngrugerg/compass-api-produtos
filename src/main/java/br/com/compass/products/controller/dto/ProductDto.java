package br.com.compass.products.controller.dto;

import org.springframework.data.domain.Page;

import br.com.compass.products.model.Product;

public class ProductDto {

	private Integer id;
	private String name;
	private String description;
	private Double price;

	public ProductDto(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
	}

	public String getDescription() {
		return description;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}

	public static Page<ProductDto> convert(Page<Product> products) {
		return products.map(ProductDto::new);
	}

}
