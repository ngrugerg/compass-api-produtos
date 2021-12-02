package br.com.compass.products.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compass.products.model.Product;

public interface ProductsRepository extends JpaRepository<Product, Integer> {
	
	Page<Product> findByName(String name, Pageable pageable);
	
}
