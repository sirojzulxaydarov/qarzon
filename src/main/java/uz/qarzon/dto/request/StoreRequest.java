package uz.qarzon.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String address;
}
