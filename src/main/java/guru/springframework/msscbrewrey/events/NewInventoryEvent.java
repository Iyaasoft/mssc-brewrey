package guru.springframework.msscbrewrey.events;

import guru.springframework.msscbrewrey.web.model.BeerDto;

public class NewInventoryEvent extends BeerEvent {

    private static final long serialVersionUID = 1737265622440031665L;

    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
