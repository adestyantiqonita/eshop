package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> findAllByAuthor(String author);
    Order findById(String id);
    Order updateStatus(String orderId, String status);
}