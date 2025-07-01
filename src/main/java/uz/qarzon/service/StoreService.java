package uz.qarzon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.qarzon.dto.request.StoreRequest;
import uz.qarzon.dto.response.StoreResponse;
import uz.qarzon.entity.Store;
import uz.qarzon.entity.User;
import uz.qarzon.entity.enums.Role;
import uz.qarzon.exception.BadRequestException;
import uz.qarzon.exception.ResourceNotFoundException;
import uz.qarzon.mapper.StoreMapper;
import uz.qarzon.repository.StoreRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepo storeRepo;
    private final BaseService baseService;

    public List<StoreResponse> getAllStore() {
        User currentUser = baseService.getCurrentUser();

        if (currentUser.getRole() == Role.ADMIN) {
            return storeRepo.findAll().stream()
                    .map(StoreMapper::toResponse)
                    .collect(Collectors.toList());
        }

        return storeRepo.findAllByOwner(currentUser).stream()
                .map(StoreMapper::toResponse)
                .collect(Collectors.toList());
    }

    public StoreResponse getStoreById(Integer storeId) {
        Store store = storeRepo.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));

        User currentUser = baseService.getCurrentUser();

        if (!store.getOwner().getId().equals(currentUser.getId())) {
            throw new BadRequestException("Access denied");
        }
        return StoreMapper.toResponse(store);
    }

    public StoreResponse createStore(StoreRequest storeRequest) {
        User currentUser = baseService.getCurrentUser();

        Store store = StoreMapper.toEntity(storeRequest, currentUser);

        Store savedStore = storeRepo.save(store);

        return StoreMapper.toResponse(savedStore);

    }

    public StoreResponse updateStore(Integer storeId, StoreRequest storeRequest) {
        Store store = storeRepo.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + storeId));

        User currentUser = baseService.getCurrentUser();

        if (!store.getOwner().getId().equals(currentUser.getId())) {
            throw new BadRequestException("Access denied");
        }

        store.setName(storeRequest.getName());
        store.setAddress(storeRequest.getAddress());

        return StoreMapper.toResponse(storeRepo.save(store));

    }

    public void deleteStore(Integer storeId) {
        Store store = storeRepo.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + storeId));

        User currentUser = baseService.getCurrentUser();

        if (!store.getOwner().getId().equals(currentUser.getId())) {
            throw new BadRequestException("Access denied");
        }

        storeRepo.delete(store);
    }

}
