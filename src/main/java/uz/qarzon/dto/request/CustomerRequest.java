package uz.qarzon.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String phoneNumber;

    private String address;

    @NotBlank
    private Integer storeId;
}
