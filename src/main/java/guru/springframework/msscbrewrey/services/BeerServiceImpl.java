package guru.springframework.msscbrewrey.services;

import guru.springframework.msscbrewrey.domain.Beer;
import guru.springframework.msscbrewrey.exception.BeerNotFoundException;
import guru.springframework.msscbrewrey.repository.BeerRepository;
import guru.springframework.msscbrewrey.web.mapper.BeerMapper;
import guru.springframework.msscbrewrey.web.mapper.BeerMapperDecorator;
import guru.springframework.msscbrewrey.web.model.BeerDto;
import guru.springframework.msscbrewrey.web.model.BeerPageList;
import guru.springframework.msscbrewrey.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private  final BeerMapper beerMapper;
    private  final BeerRepository beerRepository;

    @Cacheable(cacheNames= {"beerCache"}, condition ="#showInventoryOnHand == false", cacheManager = "cacheManager")
    @Override
    public BeerDto getBeerById(UUID beerId, boolean showInventoryOnHand) {
        System.out.println("Invoked repository for get beer");
        ((BeerMapperDecorator)beerMapper).setShowBeerInventoryOnHand(showInventoryOnHand);
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

    @Cacheable(cacheNames = {"beerListCache"} , condition ="#showInventoryOnHand == false", cacheManager = "cacheManager")
    @Override
    public BeerPageList getBeerList(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, boolean showInventoryOnHand) {
        BeerPageList pageList= null;
        Page<Beer> page = null;
        System.out.println("Invoked repository for beer list");

        if(Objects.nonNull(beerStyle) && StringUtils.isNoneEmpty(beerName)) {
            page = beerRepository.findByBeerNameAndBeerStyle(pageRequest,beerName,beerStyle.toString());
        }
        else if(Objects.nonNull(beerName) && StringUtils.isNoneEmpty(beerName)){
            page = beerRepository.findByBeerName(pageRequest,beerName);
        }
        else if(Objects.nonNull(beerName) && StringUtils.isEmpty(beerName) && Objects.isNull(beerStyle)) {
            page = beerRepository.findByBeerStyle(pageRequest,beerName);
        } else {
           page = beerRepository.findAll(pageRequest);
        }
        List<Beer> beers = (List<Beer>) beerRepository.findAll();
        ((BeerMapperDecorator)beerMapper).setShowBeerInventoryOnHand(showInventoryOnHand);
        pageList = new BeerPageList(page
                .getContent()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList()),
                PageRequest.of(
                                page.getPageable().getPageNumber(),
                                page.getPageable().getPageSize()),
                page.getTotalElements());
        return pageList;
    }
}
