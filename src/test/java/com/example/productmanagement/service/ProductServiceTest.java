package com.example.productmanagement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.productmanagement.model.Product;
import com.example.productmanagement.repository.ProductRepository;

public class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductServiceImpl productService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSaveProduct_NewProduct() {
		// Create a new product without createdAt and updatedAt
		Product newProduct = new Product("1", "Test Product", "Description", 100.0, "Category", 10);

		// Mock behavior of repository
		when(productRepository.save(any(Product.class))).thenReturn(newProduct);

		// Call the service method
		Product savedProduct = productService.saveProduct(newProduct);

		// Verify that save method was called once
		verify(productRepository, times(1)).save(any(Product.class));

		// Assert that createdAt and updatedAt are set
		assertNotNull(savedProduct.getCreatedAt());
		assertNotNull(savedProduct.getUpdatedAt());
	}

	@Test
	public void testSaveProduct_ExistingProduct() {
		Date createdAt = new Date();
		Date updatedAt = new Date();
		Product existingProduct = new Product("1", "Test Product", "Description", 100.0, "Category", 10);
		existingProduct.setCreatedAt(createdAt);
		existingProduct.setUpdatedAt(updatedAt);

		when(productRepository.findById("1")).thenReturn(Optional.of(existingProduct));
		when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

		Product updatedProduct = productService.updateProduct("1", existingProduct);

		verify(productRepository, times(1)).findById("1");
		verify(productRepository, times(1)).save(any(Product.class));

		assertEquals(createdAt, updatedProduct.getCreatedAt());
	}

	@Test
    public void testUpdateProduct_ProductNotFound() {
        when(productRepository.findById("1")).thenReturn(Optional.empty());

        Product updatedProduct = productService.updateProduct("1", new Product());

        verify(productRepository, times(1)).findById("1");

        assertNull(updatedProduct);
    }

}
