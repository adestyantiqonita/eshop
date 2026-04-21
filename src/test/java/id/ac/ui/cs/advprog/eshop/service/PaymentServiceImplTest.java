package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    Order order;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Safira");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testAddPayment() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertNotNull(result.getId());
        assertEquals("VOUCHER", result.getMethod());
    }

    @Test
    void testGetPaymentIfFound() {
        Payment payment = new Payment("p-123", order, "VOUCHER", new HashMap<>());
        when(paymentRepository.findById("p-123")).thenReturn(payment);

        Payment result = paymentService.getPayment("p-123");
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment("p-1", order, "VOUCHER", new HashMap<>()));
        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();
        assertEquals(1, result.size());
    }

    // --- VOUCHER SUB-FEATURE TESTS ---

    @Test
    void testAddPaymentVoucherSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678"); // 16 chars, starts ESHOP, 8 digits

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
    }

    @Test
    void testAddPaymentVoucherRejectedInvalidLength() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123456789"); // < 16 chars

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    }

    @Test
    void testAddPaymentVoucherRejectedNoESHOP() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "SALE123456789012"); // No ESHOP prefix

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    }

    @Test
    void testAddPaymentVoucherRejectedNot8Digits() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP12345ABCDEF"); // Only 5 digits

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    }

    // --- CASH ON DELIVERY SUB-FEATURE TESTS ---

    @Test
    void testAddPaymentCODSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Fasilkom UI, Depok");
        paymentData.put("deliveryFee", "10000");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
    }

    @Test
    void testAddPaymentCODRejectedEmptyAddress() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "10000");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    }

    @Test
    void testAddPaymentCODRejectedNullFee() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Fasilkom UI");
        // deliveryFee is null (not put)

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    }

    // --- STATUS UPDATE TESTS ---

    @Test
    void testSetStatusSuccessUpdatesOrder() {
        Payment payment = new Payment("p-123", order, "VOUCHER", new HashMap<>());
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }

    @Test
    void testSetStatusRejectedUpdatesOrderFailed() {
        Payment payment = new Payment("p-123", order, "VOUCHER", new HashMap<>());
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testGetPaymentNotFound() {
        when(paymentRepository.findById("p-kosong")).thenReturn(null);
        Payment result = paymentService.getPayment("p-kosong");
        assertNull(result);
    }

    @Test
    void testSetStatusInvalid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("p-1", order, "VOUCHER", paymentData);

        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(payment, "STATUS_NGASAL");
        });
    }

    @Test
    void testAddPaymentWithInvalidMethod() {
        Map<String, String> paymentData = new HashMap<>();
        Payment result = paymentService.addPayment(order, "BITCOIN", paymentData);

        assertNull(result);
    }

    @Test
    void testAddPaymentVoucherRejectedNullVoucherCode() {
        Map<String, String> paymentData = new HashMap<>();

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    }

    @Test
    void testAddPaymentCODRejectedNullAddress() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("deliveryFee", "10000");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    }

    @Test
    void testAddPaymentCODRejectedEmptyFee() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Fasilkom");
        paymentData.put("deliveryFee", "  ");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    }

    @Test
    void testAddPaymentVoucherNullVoucherCode() {
        Map<String, String> paymentData = new HashMap<>();

        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));
        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    }

    @Test
    void testAddPaymentCODNullAddressAndFee() {
        Map<String, String> paymentData = new HashMap<>();

        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));
        Payment result = paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    }

    @Test
    void testSetStatusNeitherSuccessNorRejected() {
        Payment payment = new Payment("p-123", order, "VOUCHER", new HashMap<>());
        payment.setStatus(PaymentStatus.WAITING_PAYMENT.getValue());

        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        paymentService.setStatus(payment, PaymentStatus.WAITING_PAYMENT.getValue());

        assertEquals(PaymentStatus.WAITING_PAYMENT.getValue(), payment.getStatus());
        assertNotEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }
}