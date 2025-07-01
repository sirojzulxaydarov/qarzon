package uz.qarzon.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.qarzon.dto.request.PaymentRequest;
import uz.qarzon.dto.response.PaymentResponse;
import uz.qarzon.entity.Customer;
import uz.qarzon.entity.Payment;
import uz.qarzon.entity.User;
import uz.qarzon.exception.AccessDeniedExceptionMe;
import uz.qarzon.exception.BadRequestException;
import uz.qarzon.exception.ResourceNotFoundException;
import uz.qarzon.mapper.PaymentMapper;
import uz.qarzon.repository.CustomerRepo;
import uz.qarzon.repository.PaymentRepo;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepo paymentRepo;
    private final CustomerRepo customerRepo;
    private final BaseService baseService;

    public PaymentResponse getPaymentById(Integer paymentId) {
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return PaymentMapper.toResponse(payment);
    }

    public List<PaymentResponse> getPaymentsByCustomerId(Integer customerId) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        User currentUser = baseService.getCurrentUser();
        if (!customer.getStore().getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedExceptionMe("Access denied");
        }

        return paymentRepo.findPaymentByCustomer(customer).stream()
                .map(PaymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        Customer customer = customerRepo.findById(paymentRequest.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        User currentUser = baseService.getCurrentUser();
        if (!customer.getStore().getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedExceptionMe("Access denied");
        }

        if (paymentRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Payment amount must be positive");
        }

        if(paymentRequest.getAmount().compareTo(customer.getCurrentBalance()) > 0) {
            throw new BadRequestException("Amount must be greater than current balance");
        }

        Payment payment = PaymentMapper.toEntity(paymentRequest, customer);
        Payment savedPayment = paymentRepo.save(payment);

        customerRepo.decreaseBalance(customer.getId(), payment.getAmount());

        return PaymentMapper.toResponse(savedPayment);
    }

    @Transactional
    public PaymentResponse updatePayment(Integer paymentId, PaymentRequest paymentRequest) {
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        Customer customer = payment.getCustomer();

        User currentUser = baseService.getCurrentUser();
        if (!payment.getCustomer().getStore().getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedExceptionMe("Access denied");
        }

        BigDecimal oldAmount = payment.getAmount();
        BigDecimal newAmount = paymentRequest.getAmount();
        BigDecimal difference = oldAmount.subtract(newAmount);


        if (difference.compareTo(BigDecimal.ZERO) > 0) {
            customerRepo.increaseBalance(payment.getCustomer().getId(), difference);
        } else if (difference.compareTo(BigDecimal.ZERO) < 0) {
            if (difference.abs().compareTo(customer.getCurrentBalance()) > 0) {
                throw new BadRequestException("Amount must be greater than current balance");
            }
            customerRepo.decreaseBalance(payment.getCustomer().getId(), difference.abs());
        }

        payment.setAmount(newAmount);
        payment.setDescription(paymentRequest.getDescription());
        payment.setPaymentDate(paymentRequest.getPaymentDate());

        Payment savedPayment = paymentRepo.save(payment);


        return PaymentMapper.toResponse(savedPayment);
    }

    @Transactional
    public void deletePayment(Integer paymentId) {
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        User currentUser = baseService.getCurrentUser();
        if (!payment.getCustomer().getStore().getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedExceptionMe("Access denied");
        }

        customerRepo.increaseBalance(payment.getCustomer().getId(), payment.getAmount());
        paymentRepo.delete(payment);
    }

}
