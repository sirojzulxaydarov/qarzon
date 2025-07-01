package uz.qarzon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.qarzon.entity.Customer;
import uz.qarzon.entity.Payment;

import java.util.List;

public interface PaymentRepo extends JpaRepository<Payment, Integer> {
    List<Payment> findPaymentByCustomer(Customer customer);
}
