package guru.springframework.msscbrewrey.services;

import guru.springframework.msscbrewrey.web.model.BearStyleEnum;
import guru.springframework.msscbrewrey.web.model.BeerDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public BeerDto getBeerById(UUID beerId) {
        return BeerDto.builder().id(UUID.randomUUID()).beerName("Galaxy Cat").beerStyle(BearStyleEnum.Ale).build();
    }

    @Override
    public BeerDto createBeer(BeerDto beerDto) {
        return BeerDto.builder()
                .id( UUID.randomUUID())
                .beerStyle(BearStyleEnum.Larger)
                .beerName("Heiniken").build();
    }

    @Override
    public void updateBeer(UUID beerId, BeerDto beerDto) {
         // update beer in db
    }

    @Override
    public void deleteBeer(UUID beerId) {
        // delete beer in db
    }
}
