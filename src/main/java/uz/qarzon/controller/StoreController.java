package uz.qarzon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qarzon.dto.request.StoreRequest;
import uz.qarzon.dto.response.StoreResponse;
import uz.qarzon.service.StoreService;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<List<StoreResponse>> getAllStores() {
        return ResponseEntity.ok(storeService.getAllStore());
    }

    @GetMapping("{storeId}")
    public ResponseEntity<StoreResponse> getStoreById(@PathVariable Integer storeId) {
        return ResponseEntity.ok(storeService.getStoreById(storeId));
    }

    @PostMapping
    public ResponseEntity<StoreResponse> createStore(@RequestBody StoreRequest storeRequest) {
        StoreResponse createdStore = storeService.createStore(storeRequest);
        return new ResponseEntity<>(createdStore, HttpStatus.CREATED);
    }

    @PutMapping("/{storeId}")
    public ResponseEntity<StoreResponse> updateStore(@PathVariable Integer storeId, @RequestBody StoreRequest  storeRequest) {
        StoreResponse updatedStore = storeService.updateStore(storeId, storeRequest);
        return new ResponseEntity<>(updatedStore, HttpStatus.OK);
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<Void> deleteStore(@PathVariable Integer storeId) {
        storeService.deleteStore(storeId);
        return ResponseEntity.noContent().build();
    }

}
