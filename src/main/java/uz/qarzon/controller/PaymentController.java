package uz.qarzon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qarzon.dto.request.PaymentRequest;
import uz.qarzon.dto.response.PaymentResponse;
import uz.qarzon.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable("paymentId") Integer paymentId) {
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByCustomerId(@PathVariable("customerId") Integer customerId) {
        return ResponseEntity.ok(paymentService.getPaymentsByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> savePayment(@RequestBody PaymentRequest paymentRequest) {
        return new ResponseEntity<>(paymentService.createPayment(paymentRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> updatePayment(@PathVariable("paymentId") Integer paymentId, @RequestBody PaymentRequest paymentRequest) {
        return new ResponseEntity<>(paymentService.updatePayment(paymentId, paymentRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> deletePayment(@PathVariable("paymentId") Integer paymentId) {
        paymentService.deletePayment(paymentId);
        return ResponseEntity.noContent().build();
    }
}