package uz.qarzon.mapper;

import uz.qarzon.dto.request.PaymentRequest;
import uz.qarzon.dto.response.PaymentResponse;
import uz.qarzon.entity.Customer;
import uz.qarzon.entity.Payment;

public class PaymentMapper {

    public static PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getId())
                .amount(payment.getAmount())
                .description(payment.getDescription())
                .paymentDate(payment.getPaymentDate())
                .customerId(payment.getCustomer().getId())
                .build();
    }

    public static Payment toEntity(PaymentRequest paymentRequest, Customer customer) {
        return Payment.builder()
                .amount(paymentRequest.getAmount())
                .description(paymentRequest.getDescription())
                .paymentDate(paymentRequest.getPaymentDate())
                .customer(customer)
                .build();
    }

}
