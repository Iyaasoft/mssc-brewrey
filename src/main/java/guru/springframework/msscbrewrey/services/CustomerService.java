package guru.springframework.msscbrewrey.services;

import guru.springframework.web.model.CustomerDto;

public interface CustomerService {

    CustomerDto getCustomer(String id);
    CustomerDto createCustomer(CustomerDto customerDto);
    void updateCustomer(CustomerDto customer);
    void deleteCustomer(String customerId);
}
