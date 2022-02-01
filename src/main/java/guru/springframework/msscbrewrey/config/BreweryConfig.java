package guru.springframework.msscbrewrey.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "sgf.brewery", ignoreInvalidFields = true)
public abstract class BreweryConfig {

    public void setInventoryServiceHost(String inventoryServiceHost) {
        this.inventoryServiceHost = inventoryServiceHost;
    }

    protected String inventoryServiceHost;
}