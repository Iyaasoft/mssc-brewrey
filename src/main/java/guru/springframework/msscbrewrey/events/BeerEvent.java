package guru.springframework.msscbrewrey.events;

import guru.springframework.msscbrewrey.web.model.BeerDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class BeerEvent implements Serializable {

    private final BeerDto beerDto;

    public BeerEvent(BeerDto beerDto) {
        this.beerDto = beerDto;
    }
}
