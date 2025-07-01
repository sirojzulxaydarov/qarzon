package uz.qarzon.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {

    private Integer paymentId;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String description;
    private Integer customerId;
}
