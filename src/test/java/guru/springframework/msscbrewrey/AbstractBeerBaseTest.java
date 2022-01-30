package guru.springframework.msscbrewrey;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewrey.bootstrap.BeerDbDataInitializer;
import guru.springframework.msscbrewrey.services.BeerService;
import guru.springframework.msscbrewrey.services.CustomerService;
import guru.springframework.msscbrewrey.web.model.BearStyleEnum;
import guru.springframework.msscbrewrey.web.model.BeerDto;
import guru.springframework.msscbrewrey.web.model.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;


public class AbstractBeerBaseTest {

    @Autowired
    protected MockMvc mvc;

    @MockBean
    protected BeerService beerService;

    @MockBean
    protected CustomerService customerService;

    @Autowired
    protected ObjectMapper mapper;

    protected BeerDto getBeerDto() {
        return BeerDto.builder()
                .beerName("Heiniken")
                .beerStyle(BearStyleEnum.Larger)
                .price(new BigDecimal(2.50))
                .upc(BeerDbDataInitializer.BEER_1_UPC)
                .minOnHand(36)
                .quantityOnHand(230).build();
    }

    protected CustomerDto getCustomer() {
       return CustomerDto.builder()
                .name("James Brown II")
                .build();
    }
}
