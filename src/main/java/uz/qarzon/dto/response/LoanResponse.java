package uz.qarzon.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanResponse {

    private Integer loanId;
    private BigDecimal amount;
    private LocalDate loanDate;
    private String description;
    private Integer customerId;

}
