package guru.springframework.msscbrewrey.events;

import guru.springframework.msscbrewrey.web.model.BeerDto;


public class NewInventoryEvent extends BeerEvent {

    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
