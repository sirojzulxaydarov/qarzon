package uz.qarzon.mapper;

import uz.qarzon.dto.request.CustomerRequest;
import uz.qarzon.dto.response.CustomerResponse;
import uz.qarzon.entity.Customer;
import uz.qarzon.entity.Store;

import java.math.BigDecimal;

public class CustomerMapper {

    public static CustomerResponse toResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .storeId(customer.getStore().getId())
                .currentBalance(customer.getCurrentBalance())
                .build();
    }

    public static Customer toEntity(CustomerRequest request, Store store) {
        return Customer.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .store(store)
                .build();
    }

}
