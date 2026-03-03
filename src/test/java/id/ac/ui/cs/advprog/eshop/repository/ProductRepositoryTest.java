package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-4600-8860-71af6af63b06");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-4600-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testUpdateProductPositive() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-4600-8860-71af6af63b06");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-4600-8860-71af6af63b06");
        updatedProduct.setProductName("Sampo Cap Usep");
        updatedProduct.setProductQuantity(50);
        productRepository.update(updatedProduct);

        Product savedProduct = productRepository.findById(product.getProductId());
        assertEquals("Sampo Cap Usep", savedProduct.getProductName());
        assertEquals(50, savedProduct.getProductQuantity());
    }

    @Test
    void testUpdateProductNegative() {
        Product product = new Product();
        product.setProductId("id-yang-ada");
        productRepository.create(product);

        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("id-tidak-ada");
        nonExistentProduct.setProductName("Barang Ghaib");

        Product result = productRepository.update(nonExistentProduct);
        assertNull(result);
    }

    @Test
    void testDeleteProductPositive() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-4600-8860-71af6af63b06");
        productRepository.create(product);

        productRepository.delete("eb558e9f-1c39-4600-8860-71af6af63b06");

        Product result = productRepository.findById("eb558e9f-1c39-4600-8860-71af6af63b06");
        assertNull(result);
    }

    @Test
    void testDeleteProductNegative() {
        Product product = new Product();
        product.setProductId("id-asli");
        productRepository.create(product);
        productRepository.delete("id-palsu");
        Product result = productRepository.findById("id-asli");
        assertNotNull(result);
    }
}