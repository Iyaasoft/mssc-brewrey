package guru.springframework.msscbrewrey.services.inventory;

import guru.springframework.msscbrewrey.config.BreweryConfig;
import guru.springframework.web.model.BeerInventoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Qualifier("inventoryService")
@Component
public class InventoryServiceRestClientImpl extends BreweryConfig implements InventoryServiceRestClient {

    private final String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";


    RestTemplate restTemplate;

    public InventoryServiceRestClientImpl(){
        super();
    }

    public InventoryServiceRestClientImpl(RestTemplateBuilder restTemplateBuilder) {
        super();
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Integer getOnHandInventory(UUID beerId) {

        ResponseEntity<List<BeerInventoryDto>> inventory =
                restTemplate.exchange(inventoryServiceHost + INVENTORY_PATH, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<BeerInventoryDto>>() {
                        }, beerId);

        return Objects.requireNonNull(inventory.getBody())
                .stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand)
                .sum();
    }

    public void setRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

}
