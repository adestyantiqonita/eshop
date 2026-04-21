package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Map<String, String> paymentData;
    private Order order;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
        this.paymentData.put("voucherCode", "ESHOP1234ABC5678");

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Safira");
        product.setProductQuantity(2);
        products.add(product);

        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreatePaymentDefaultStatus() {
        Payment payment = new Payment("p-123", this.order, "VOUCHER", this.paymentData);
        assertEquals("p-123", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals(this.paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.WAITING_PAYMENT.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentWithStatus() {
        Payment payment = new Payment("p-123", this.order, "VOUCHER", this.paymentData, PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("p-123", this.order, "VOUCHER", this.paymentData, "MEOW");
        });
    }

    @Test
    void testCreatePaymentNullOrder() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("p-123", null, "VOUCHER", this.paymentData);
        });
    }
}