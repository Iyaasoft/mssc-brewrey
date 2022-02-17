package guru.springframework.msscbrewrey.services;

import guru.springframework.web.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    @Cacheable(cacheNames = "beerCache", key= "id" , condition ="#showInventoryOnHand == false")
    @Override
    public CustomerDto getCustomer(String id) {
        return CustomerDto.builder().id(UUID.randomUUID()).name("Abu Ayyub").build();
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        return CustomerDto.builder().id(UUID.randomUUID()).build();
    }

    @Override
    public void updateCustomer(CustomerDto customer) {
        // todo do the update to db
    }

    @Override
    public void deleteCustomer(String customerId) {
        log.info("Customer : "+ customerId + "  has been deleted");
    }
}
