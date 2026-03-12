package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreate() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        when(productRepository.create(product)).thenReturn(product);
        Product result = productService.create(product);

        verify(productRepository, times(1)).create(product);
        assertEquals(product.getProductId(), result.getProductId());
    }

    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        productList.add(product1);

        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620101");
        productList.add(product2);

        Iterator<Product> iterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();

        verify(productRepository, times(1)).findAll();
        assertEquals(2, result.size());
        assertEquals(product1.getProductId(), result.get(0).getProductId());
        assertEquals(product2.getProductId(), result.get(1).getProductId());
    }

    @Test
    void testDelete() {
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";

        // delete() di Service bertipe void, kita hanya memverifikasi pemanggilan ke repo
        productService.delete(productId);

        verify(productRepository, times(1)).delete(productId);
    }

    @Test
    void testFindById() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");

        when(productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(product);

        Product result = productService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        verify(productRepository, times(1)).findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertNotNull(result);
        assertEquals(product.getProductId(), result.getProductId());
    }

    @Test
    void testUpdate() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");

        when(productRepository.update(product)).thenReturn(product);

        Product result = productService.update(product);

        verify(productRepository, times(1)).update(product);
        assertNotNull(result);
        assertEquals(product.getProductId(), result.getProductId());
    }
}