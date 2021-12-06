package br.com.compass.products.controller.form;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.compass.products.model.Product;
import br.com.compass.products.repository.ProductsRepository;

public class ProductForm {

	@NotNull @NotEmpty @Length(min = 3, max = 30)
	private String name;
	@NotNull @NotEmpty @Length(min = 3, max = 50) 
	private String description;
	@NotNull @DecimalMin(value = "1.0")
	private Double price;

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Product converter(ProductsRepository productsRepository) {
		return new Product(name, description, price);
	}
	
	public Product update(Integer id, ProductsRepository productsRepository) {
		Product product = productsRepository.getById(id);
		
		product.setName(this.name);
		product.setDescription(this.description);
		product.setPrice(this.price);
		
		return product;
	}

}
