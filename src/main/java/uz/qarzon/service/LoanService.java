package uz.qarzon.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.qarzon.dto.request.LoanRequest;
import uz.qarzon.dto.response.LoanResponse;
import uz.qarzon.entity.Customer;
import uz.qarzon.entity.Loan;
import uz.qarzon.entity.User;
import uz.qarzon.exception.AccessDeniedExceptionMe;
import uz.qarzon.exception.BadRequestException;
import uz.qarzon.exception.ResourceNotFoundException;
import uz.qarzon.mapper.LoanMapper;
import uz.qarzon.repository.CustomerRepo;
import uz.qarzon.repository.LoanRepo;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepo loanRepo;
    private final CustomerRepo customerRepo;
    private final BaseService baseService;

    public List<LoanResponse> getLoansByCustomerId(Integer customerId) {
        Customer customer = customerRepo.findById(customerId).orElseThrow(() ->
                new ResourceNotFoundException("Customer not found"));

        User currentUser = baseService.getCurrentUser();
        if (!customer.getStore().getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedExceptionMe("Access denied");
        }

        return loanRepo.findAllByCustomer(customer).stream()
                .map(LoanMapper::toResponse)
                .collect(Collectors.toList());
    }

    public LoanResponse getLoanById(Integer id) {
        Loan loan = loanRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));
        return LoanMapper.toResponse(loan);
    }

    @Transactional
    public LoanResponse createLoan(LoanRequest loanRequest) {
        Customer customer = customerRepo.findById(loanRequest.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        User currentUser = baseService.getCurrentUser();
        if (!customer.getStore().getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedExceptionMe("Access denied");
        }

        if (loanRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Payment amount must be positive");
        }

        Loan loan = LoanMapper.toEntity(loanRequest, customer);
        Loan savedLoan = loanRepo.save(loan);

        customerRepo.increaseBalance(customer.getId(), loan.getAmount());

        return LoanMapper.toResponse(savedLoan);
    }

    @Transactional
    public LoanResponse updateLoan(Integer loanId, LoanRequest loanRequest) {
        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        User currentUser = baseService.getCurrentUser();
        if (!loan.getCustomer().getStore().getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedExceptionMe("Access denied");
        }

        BigDecimal oldAmount = loan.getAmount();
        BigDecimal newAmount = loanRequest.getAmount();
        BigDecimal difference = newAmount.subtract(oldAmount);

        if (difference.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Amount must be greater than current balance");
        }

        loan.setAmount(newAmount);
        loan.setLoanDate(loanRequest.getLoanDate());
        loan.setDescription(loanRequest.getDescription());

        Loan savedLoan = loanRepo.save(loan);

        if (difference.compareTo(BigDecimal.ZERO) > 0) {
            customerRepo.increaseBalance(loan.getCustomer().getId(), difference);
        } else if (difference.compareTo(BigDecimal.ZERO) < 0) {
            customerRepo.decreaseBalance(loan.getCustomer().getId(), difference.abs());
        }

        return LoanMapper.toResponse(savedLoan);
    }

    @Transactional
    public void deleteLoan(Integer loanId) {
        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        User currentUser = baseService.getCurrentUser();

        if (!loan.getCustomer().getStore().getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedExceptionMe("Access denied");
        }

        customerRepo.decreaseBalance(loan.getCustomer().getId(), loan.getAmount());
        loanRepo.delete(loan);
    }
}
