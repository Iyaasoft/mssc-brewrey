package guru.springframework.msscbrewrey.services.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class BeerInventoryDto {

    private UUID beerId;
    private String upc;
    private Integer quantityOnHand = 0;

}
