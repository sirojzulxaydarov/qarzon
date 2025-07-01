package uz.qarzon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.qarzon.dto.request.CustomerRequest;
import uz.qarzon.dto.response.CustomerResponse;
import uz.qarzon.dto.response.CustomerStatisticResponse;
import uz.qarzon.entity.Customer;
import uz.qarzon.entity.Store;
import uz.qarzon.entity.User;
import uz.qarzon.exception.AccessDeniedExceptionMe;
import uz.qarzon.exception.ResourceNotFoundException;
import uz.qarzon.mapper.CustomerMapper;
import uz.qarzon.repository.CustomerRepo;
import uz.qarzon.repository.StoreRepo;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final StoreRepo storeRepo;
    private final BaseService baseService;

    public List<CustomerResponse> getAllByUser() {
        User currentUser = baseService.getCurrentUser();
        return customerRepo.findAllByStoreOwner(currentUser).stream()
                .map(CustomerMapper::toResponse)
                .collect(Collectors.toList());

    }

    public List<CustomerResponse> getAllByStoreId(Integer storeId) {
        User currentUser = baseService.getCurrentUser();
        Store store = storeRepo.findById(storeId).orElseThrow(() -> new ResourceNotFoundException("Store not found"));

        if (!store.getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedExceptionMe("Access denied");
        }

        return customerRepo.findAllByStore(store).stream()
                .map(CustomerMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse getByCustomerId(Integer customerId) {
        User currentUser = baseService.getCurrentUser();
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        if (!customer.getStore().getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedExceptionMe("Access denied");
        }

        return CustomerMapper.toResponse(customer);
    }

    public CustomerResponse createCustomer(CustomerRequest  customerRequest) {
        User currentUser = baseService.getCurrentUser();
        Store store = storeRepo.findById(customerRequest.getStoreId())
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));

        if (!store.getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedExceptionMe("Access denied");
        }

        Customer customer = CustomerMapper.toEntity(customerRequest, store);
        customer.setCurrentBalance(BigDecimal.ZERO);

        return CustomerMapper.toResponse(customerRepo.save(customer));

    }

    public CustomerResponse updateCustomer(Integer customerId, CustomerRequest customerRequest) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        User currentUser = baseService.getCurrentUser();
        if (!customer.getStore().getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedExceptionMe("Access denied");
        }

        customer.setName(customerRequest.getName());
        customer.setAddress(customerRequest.getAddress());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        customer.setStore(customer.getStore());

        return CustomerMapper.toResponse(customerRepo.save(customer));
    }

    public void deleteCustomer(Integer customerId) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        User currentUser = baseService.getCurrentUser();
        if (!customer.getStore().getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedExceptionMe("Access denied");
        }

        customerRepo.delete(customer);
    }

    public CustomerStatisticResponse getCustomerStatistic(Integer storeId) {

        int countCustomers = customerRepo.countCustomerByStoreId(storeId);
        int countDebtCustomers = customerRepo.countDebtCustomerByStoreId(storeId);
        BigDecimal debtCustomersSum = customerRepo.getTotalDebtCustomersSum(storeId);

        return new CustomerStatisticResponse(countCustomers,countDebtCustomers,debtCustomersSum);
    }



}
