package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String status;

        if (method.equals("VOUCHER")) {
            status = validateVoucher(paymentData);
        } else if (method.equals("CASH_ON_DELIVERY")) {
            status = validateCOD(paymentData);
        } else {
            status = PaymentStatus.REJECTED.getValue();
        }

        Payment payment = new Payment(UUID.randomUUID().toString(), order, method, paymentData, status);
        return paymentRepository.save(payment);
    }

    private String validateVoucher(Map<String, String> paymentData) {
        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode == null || voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            return PaymentStatus.REJECTED.getValue();
        }

        long digitCount = voucherCode.chars().filter(Character::isDigit).count();
        return (digitCount == 8) ? PaymentStatus.SUCCESS.getValue() : PaymentStatus.REJECTED.getValue();
    }

    private String validateCOD(Map<String, String> paymentData) {
        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");
        if (address == null || address.trim().isEmpty() || deliveryFee == null || deliveryFee.trim().isEmpty()) {
            return PaymentStatus.REJECTED.getValue();
        }
        return PaymentStatus.SUCCESS.getValue();
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);
        if (status.equals(PaymentStatus.SUCCESS.getValue())) {
            payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
        } else if (status.equals(PaymentStatus.REJECTED.getValue())) {
            payment.getOrder().setStatus(OrderStatus.FAILED.getValue());
        }
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}