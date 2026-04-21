package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Map<String, String> paymentData;
    private Order order;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
        this.paymentData.put("voucherCode", "ESHOP1234ABC5678");
        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                new ArrayList<>(), 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreatePaymentDefaultStatus() {
        Payment payment = new Payment("p-123", this.order, "VOUCHER", this.paymentData);
        assertEquals("p-123", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals(this.paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.WAITING_PAYMENT.getValue(), payment.getStatus());
    }
}