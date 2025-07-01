package uz.qarzon.mapper;

import uz.qarzon.dto.request.LoanRequest;
import uz.qarzon.dto.response.LoanResponse;
import uz.qarzon.entity.Customer;
import uz.qarzon.entity.Loan;

public class LoanMapper {

    public static LoanResponse toResponse(Loan loan) {
        return LoanResponse.builder()
                .loanId(loan.getId())
                .amount(loan.getAmount())
                .loanDate(loan.getLoanDate())
                .description(loan.getDescription())
                .customerId(loan.getCustomer().getId())
                .build();
    }

    public static Loan toEntity(LoanRequest loanRequest, Customer customer) {
        return Loan.builder()
                .amount(loanRequest.getAmount())
                .loanDate(loanRequest.getLoanDate())
                .description(loanRequest.getDescription())
                .customer(customer)
                .build();
    }
}
