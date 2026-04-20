package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderRepositoryTest {
    OrderRepository orderRepository;
    List<Order> orders;

    @BeforeEach
    void setUp() {
        orderRepository = new OrderRepository();
        orders = new ArrayList<>();

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        Order order1 = new Order("13652556-012a-4c07-b546-bc13f31eb35d", products, 1708560000L, "Bambang Sugeni");
        orders.add(order1);
        Order order2 = new Order("7f9e15ad-687e-4a2f-9170-65924aa1f72d", products, 1708570000L, "Siti Purwanti");
        orders.add(order2);
        Order order3 = new Order("e3340295-64c5-4458-957f-05f40f350ad5", products, 1708580000L, "Bambang Sugeni");
        orders.add(order3);
    }

    @Test
    void testSaveSuccess() {
        Order order = orders.get(0);
        Order result = orderRepository.save(order);

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getProducts(), result.getProducts());
        assertEquals(order.getOrderTime(), result.getOrderTime());
        assertEquals(order.getAuthor(), result.getAuthor());
        assertEquals(order.getStatus(), result.getStatus());
    }

    @Test
    void testSaveUpdate() {
        Order order = orders.get(0);
        orderRepository.save(order);

        Order newOrder = new Order(order.getId(), order.getProducts(), order.getOrderTime(), order.getAuthor(), OrderStatus.SUCCESS.getValue());
        Order result = orderRepository.save(newOrder);

        assertEquals(order.getId(), result.getId());
        assertEquals(OrderStatus.SUCCESS.getValue(), result.getStatus());
    }

    @Test
    void testFindByIdSuccess() {
        for (Order order : orders) {
            orderRepository.save(order);
        }

        Order order = orders.get(1);
        Order result = orderRepository.findById(order.getId());

        assertEquals(order.getId(), result.getId());
    }

    @Test
    void testFindByIdNotFound() {
        for (Order order : orders) {
            orderRepository.save(order);
        }

        Order result = orderRepository.findById("6c8e3c57-fdeb-48d3-be33-c300836135ca");
        assertNull(result);
    }

    @Test
    void testFindAllByAuthor() {
        for (Order order : orders) {
            orderRepository.save(order);
        }

        List<Order> result = orderRepository.findAllByAuthor("Bambang Sugeni");
        assertEquals(2, result.size());
    }
}