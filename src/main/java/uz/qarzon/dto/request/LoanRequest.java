package uz.qarzon.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanRequest {

    private BigDecimal amount;
    private LocalDate loanDate;
    private String description;
    private Integer customerId;

}
