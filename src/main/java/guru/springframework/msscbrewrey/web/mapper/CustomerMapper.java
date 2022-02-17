package guru.springframework.msscbrewrey.web.mapper;

import guru.springframework.msscbrewrey.domain.Customer;
import guru.springframework.web.model.CustomerDto;
import org.mapstruct.Mapper;

@Mapper( componentModel = "spring", uses={DateMapper.class})
public interface CustomerMapper {

    CustomerDto CustomerToCustomerDto(Customer customer);
    Customer CustomerDtoToCustomer(CustomerDto customerDto);
}
