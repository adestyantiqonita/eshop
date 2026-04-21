package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/create")
    public String createOrderPage(Model model) {
        return "order/createOrder";
    }

    @GetMapping("/history")
    public String orderHistoryPage(Model model) {
        return "order/orderHistory";
    }

    @PostMapping("/history")
    public String orderHistoryPost(@RequestParam("author") String author, Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("orders", orders);
        return "order/orderHistory";
    }

    @GetMapping("/pay/{orderId}")
    public String paymentOrderPage(@PathVariable("orderId") String orderId, Model model) {
        // Untuk sementara kita arahkan ke template paymentOrder
        // Pastikan file paymentOrder.html nanti ada di folder templates/order/
        model.addAttribute("orderId", orderId);
        return "order/paymentOrder";
    }
}