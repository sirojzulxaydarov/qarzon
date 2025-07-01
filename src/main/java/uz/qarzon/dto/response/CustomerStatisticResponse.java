package uz.qarzon.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerStatisticResponse {

    private Integer totalCustomers;
    private Integer totalDebtCustomers;
    private BigDecimal totalDebtCustomersSum;
}
