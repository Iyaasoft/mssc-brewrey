package guru.springframework.msscbrewrey.web.mapper;

import guru.springframework.msscbrewrey.domain.Beer;
import guru.springframework.msscbrewrey.services.inventory.InventoryServiceRestClient;
import guru.springframework.msscbrewrey.services.inventory.InventoryServiceRestClientImpl;
import guru.springframework.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;

public abstract class BeerMapperDecorator implements BeerMapper {


    public void setShowBeerInventoryOnHand(boolean showBeerInventory) {
        this.showBeerInventoryOnHand = showBeerInventory;
    }

    private boolean showBeerInventoryOnHand = false;
    private BeerMapper beerMapper;
    private InventoryServiceRestClient inventoryService;

    @Autowired
    public void setBeerMapper(BeerMapper beerMapper) {
        this.beerMapper = beerMapper;
    }

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    @Qualifier("inventoryService")
    public void setInventoryService(InventoryServiceRestClient inventoryService) {
        this.inventoryService = inventoryService;
        ((InventoryServiceRestClientImpl)inventoryService).setRestTemplate(restTemplateBuilder);

    }

    @Override
    public BeerDto beerToBeerDto(Beer beer) {
        BeerDto dto = beerMapper.beerToBeerDto(beer);
        if(showBeerInventoryOnHand) {
            dto.setQuantityOnHand(inventoryService.getOnHandInventory(dto.getId()));
        }
        return dto;
    }

    @Override
    public Beer beerDtoToBeer(BeerDto beerDto) {
        return beerMapper.beerDtoToBeer(beerDto);
    }
}
