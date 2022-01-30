package guru.springframework.msscbrewrey.services;

import guru.springframework.msscbrewrey.domain.Beer;
import guru.springframework.msscbrewrey.exception.BeerNotFoundException;
import guru.springframework.msscbrewrey.repository.BeerRepository;
import guru.springframework.msscbrewrey.web.mapper.BeerMapper;
import guru.springframework.msscbrewrey.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerMapper beerMapper;
    private final BeerRepository beerRepository;

    @Override
    public BeerDto getBeerById(UUID beerId) {
            return beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(() -> new BeerNotFoundException()));
    }

    @Override
    public BeerDto createBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Optional<Beer> beerOpt= beerRepository.findById(beerId);
        Beer beer = null;
        if(beerOpt.isPresent()) {
            beer = beerOpt.get();
            beer.setBeerStyle(beerDto.getBeerStyle().toString());
            beer.setBeerName(beerDto.getBeerName());
            beer.setUpc(beerDto.getUpc());
            beer.setQuantityOnHand(beerDto.getQuantityOnHand());
            beer.setMinOnHand(beerDto.getMinOnHand());
        } else {
            beer = beerMapper.beerDtoToBeer(beerDto);
            beer.setId(beerId);
        }
        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }

    @Override
    public void deleteBeer(UUID beerId) {
        beerRepository.deleteById(beerId);
    }
}
