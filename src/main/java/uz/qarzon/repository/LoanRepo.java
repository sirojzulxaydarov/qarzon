package uz.qarzon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.qarzon.entity.Customer;
import uz.qarzon.entity.Loan;

import java.util.List;

public interface LoanRepo extends JpaRepository<Loan, Integer> {
    List<Loan> findAllByCustomer(Customer customer);
}
