package com.example.productmanagement.service;

import java.util.List;

import com.example.productmanagement.model.Product;

public interface ProductService {
	List<Product> getAllProducts();

	Product getProductById(String id);

	Product saveProduct(Product product);

	void deleteProduct(String id);

	Product updateProduct(String id, Product product);
}