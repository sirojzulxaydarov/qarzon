package uz.qarzon.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qarzon.dto.request.CustomerRequest;
import uz.qarzon.dto.response.CustomerResponse;
import uz.qarzon.dto.response.CustomerStatisticResponse;
import uz.qarzon.service.CustomerService;
import uz.qarzon.service.ExportService;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    private final ExportService exportService;

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllByOwner() {
        return ResponseEntity.ok(customerService.getAllByUser());
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<CustomerResponse>> getAllByStore(@PathVariable Integer storeId) {
        return ResponseEntity.ok(customerService.getAllByStoreId(storeId));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getByCustomersId(@PathVariable Integer customerId) {
        return ResponseEntity.ok(customerService.getByCustomerId(customerId));
    }


    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        return new ResponseEntity<>(customerService.createCustomer(customerRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Integer customerId,
            @RequestBody CustomerRequest customerRequest) {
        return new ResponseEntity<>(customerService.updateCustomer(customerId, customerRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> deleteCustomer(@PathVariable Integer customerId) {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/store/{storeId}/statistic")
    public ResponseEntity<CustomerStatisticResponse> getCustomerStatistic(@PathVariable Integer storeId) {
        return ResponseEntity.ok(customerService.getCustomerStatistic(storeId));
    }

    @GetMapping("store/{storeId}/export")
    public ResponseEntity<ByteArrayResource> exportDebtCustomers(@PathVariable Integer storeId) {

        byte[] exported = exportService.exportDebtCustomersByStoreId(storeId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=customers.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(exported.length)
                .body(new ByteArrayResource(exported));
    }

}
