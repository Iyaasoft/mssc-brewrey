package guru.springframework.msscbrewrey.services;

import guru.springframework.msscbrewrey.web.model.BeerDto;
import guru.springframework.msscbrewrey.web.model.BeerPageList;
import guru.springframework.msscbrewrey.web.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public interface BeerService {

    BeerDto getBeerById(UUID beerId,  boolean showAllInventoryOnHand);
    BeerDto createBeer(BeerDto beerDto);
    BeerDto updateBeer(UUID beerId, BeerDto beerDto);
    void deleteBeer(UUID beerId);

    BeerPageList getBeerList(String beerName, BeerStyleEnum beerStyle, PageRequest of, boolean showInventoryOnHand);

    BeerDto getBeerByUpc(String upc, boolean showAllInventoryOnHand);
}
