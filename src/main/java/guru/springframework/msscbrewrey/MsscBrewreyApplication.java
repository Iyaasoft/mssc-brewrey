package guru.springframework.msscbrewrey;

import guru.springframework.msscbrewrey.services.inventory.InventoryServiceRestClient;
import guru.springframework.msscbrewrey.services.inventory.InventoryServiceRestClientImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MsscBrewreyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsscBrewreyApplication.class, args);
	}

	@Qualifier("inventoryService")
	public InventoryServiceRestClientImpl getInventoryServiceRestClientImpl() {
		return new InventoryServiceRestClientImpl();
	}
}
