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
		// Existing product with createdAt and updatedAt set
		Date createdAt = new Date();
		Date updatedAt = new Date();
		Product existingProduct = new Product("1", "Test Product", "Description", 100.0, "Category", 10);
		existingProduct.setCreatedAt(createdAt);
		existingProduct.setUpdatedAt(updatedAt);

		// Mock behavior of repository
		when(productRepository.findById("1")).thenReturn(Optional.of(existingProduct));
		when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

		// Call the service method
		Product updatedProduct = productService.updateProduct("1", existingProduct);

		// Verify that findById and save methods were called
		verify(productRepository, times(1)).findById("1");
		verify(productRepository, times(1)).save(any(Product.class));

		// Assert that updatedAt is updated but createdAt remains unchanged
		assertEquals(createdAt, updatedProduct.getCreatedAt());
	}

	@Test
    public void testUpdateProduct_ProductNotFound() {
        // Mock behavior of repository for a non-existent product
        when(productRepository.findById("1")).thenReturn(Optional.empty());

        // Call the service method
        Product updatedProduct = productService.updateProduct("1", new Product());

        // Verify that findById was called
        verify(productRepository, times(1)).findById("1");

        // Assert that null is returned for non-existent product
        assertNull(updatedProduct);
    }

	// Add more test cases to cover all branches in updateProduct method
}
