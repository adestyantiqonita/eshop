package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private Order sampleOrder;
    private Payment samplePayment;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();

        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Safira");
        product.setProductQuantity(2);

        List<Product> products = new ArrayList<>();
        products.add(product);

        sampleOrder = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        samplePayment = new Payment("p-123", sampleOrder, "VOUCHER", paymentData);
    }

    @Test
    void testPaymentDetailPage() throws Exception {
        mockMvc.perform(get("/payment/detail"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/paymentDetail"));
    }

    @Test
    void testPaymentDetailWithIdPage() throws Exception {
        when(paymentService.getPayment("p-123")).thenReturn(samplePayment);

        mockMvc.perform(get("/payment/detail/p-123"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/paymentDetail"))
                .andExpect(model().attribute("payment", samplePayment));
    }

    @Test
    void testAdminListPage() throws Exception {
        List<Payment> payments = new ArrayList<>();
        payments.add(samplePayment);
        when(paymentService.getAllPayments()).thenReturn(payments);

        mockMvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/adminList"))
                .andExpect(model().attribute("payments", payments));
    }

    @Test
    void testAdminDetailPage() throws Exception {
        when(paymentService.getPayment("p-123")).thenReturn(samplePayment);

        mockMvc.perform(get("/payment/admin/detail/p-123"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/adminDetail"))
                .andExpect(model().attribute("payment", samplePayment));
    }

    @Test
    void testSetStatusPost() throws Exception {
        when(paymentService.getPayment("p-123")).thenReturn(samplePayment);

        mockMvc.perform(post("/payment/admin/set-status/p-123")
                        .param("status", "SUCCESS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payment/admin/list"));

        verify(paymentService).setStatus(eq(samplePayment), eq("SUCCESS"));
    }
}