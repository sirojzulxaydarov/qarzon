package uz.qarzon.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {

    private BigDecimal amount;
    private LocalDate paymentDate;
    private String description;
    private Integer customerId;
}
