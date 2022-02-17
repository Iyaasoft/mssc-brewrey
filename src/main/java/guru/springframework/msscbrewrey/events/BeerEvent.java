package guru.springframework.msscbrewrey.events;

import guru.springframework.msscbrewrey.web.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class BeerEvent implements Serializable {

    private BeerDto beerDto;

    public BeerEvent(BeerDto beerDto) {
        this.beerDto = beerDto;
    }
}
