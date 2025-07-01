package uz.qarzon.mapper;

import uz.qarzon.dto.request.StoreRequest;
import uz.qarzon.dto.response.StoreResponse;
import uz.qarzon.entity.Store;
import uz.qarzon.entity.User;

public class StoreMapper {

    public static StoreResponse toResponse(Store store) {
        return StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .ownerId(store.getOwner().getId())
                .build();
    }

    public static Store toEntity(StoreRequest request, User user) {
        return Store.builder()
                .name(request.getName())
                .address(request.getAddress())
                .owner(user)
                .build();
    }

}
