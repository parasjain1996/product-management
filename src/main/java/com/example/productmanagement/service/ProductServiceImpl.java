package com.example.productmanagement.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.productmanagement.model.Product;
import com.example.productmanagement.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product getProductById(String id) {
		return productRepository.findById(id).orElse(null);
	}

	@Override
	public Product saveProduct(Product product) {
		if (null == product.getUpdatedAt()) {
			product.setUpdatedAt(new Date());
		}
		if (null == product.getCreatedAt()) {
			product.setCreatedAt(new Date());
		}
		return productRepository.save(product);
	}

	@Override
	public void deleteProduct(String id) {
		productRepository.deleteById(id);
	}

	@Override
	public Product updateProduct(String id, Product product) {
		Product existingProduct = productRepository.findById(id).orElse(null);
		if (existingProduct != null) {
			existingProduct = product;
			if (null == existingProduct.getUpdatedAt()) {
				existingProduct.setUpdatedAt(new Date());
			}
			if (null == existingProduct.getCreatedAt()) {
				existingProduct.setCreatedAt(new Date());
			}
			Product updatedProduct = productRepository.save(existingProduct);
			return updatedProduct;
		} else {
			return null;
		}
	}
}
