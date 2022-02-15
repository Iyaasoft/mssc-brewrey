package guru.springframework.msscbrewrey.events;

import guru.springframework.msscbrewrey.web.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class BeerEvent implements Serializable {

    private final BeerDto beerDto;

}
