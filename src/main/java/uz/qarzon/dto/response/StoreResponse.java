package uz.qarzon.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreResponse {

    private Integer id;
    private String name;
    private String address;
    private Integer ownerId;
}
