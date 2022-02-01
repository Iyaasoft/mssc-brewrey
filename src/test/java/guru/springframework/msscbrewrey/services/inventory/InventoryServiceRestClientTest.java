package guru.springframework.msscbrewrey.services.inventory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InventoryServiceRestClientTest {

    @Autowired
    InventoryServiceRestClient inventoryServiceRestClient;

    @Test
    public void ShouldRetrieveOnHandQuantityFromInventoryService() {
       int result =  inventoryServiceRestClient.getOnHandInventory(UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb"));

       assertTrue(result > 0);
    }


}