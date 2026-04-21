package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Order order;
    private Map<String, String> paymentData;

    public Payment(String id, Order order, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.order = order;
        this.status = PaymentStatus.WAITING_PAYMENT.getValue();

        if (order == null) {
            throw new IllegalArgumentException();
        }
    }

    public Payment(String id, Order order, String method, Map<String, String> paymentData, String status) {
        this(id, order, method, paymentData);
        this.setStatus(status);
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }
}