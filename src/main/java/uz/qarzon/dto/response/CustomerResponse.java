package uz.qarzon.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {

    private Integer id;
    private String name;
    private String phoneNumber;
    private String address;
    private BigDecimal currentBalance;
    private Integer storeId;

}
