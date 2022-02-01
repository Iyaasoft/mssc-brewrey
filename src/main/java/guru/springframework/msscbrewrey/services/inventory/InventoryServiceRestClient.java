package guru.springframework.msscbrewrey.services.inventory;

import org.springframework.web.client.RestTemplate;

import java.util.UUID;

public interface InventoryServiceRestClient {
    Integer getOnHandInventory(UUID beerId);
}
