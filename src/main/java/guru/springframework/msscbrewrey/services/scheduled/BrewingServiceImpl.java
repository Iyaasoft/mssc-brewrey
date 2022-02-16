package guru.springframework.msscbrewrey.services.scheduled;

import guru.springframework.msscbrewrey.domain.Beer;
import guru.springframework.msscbrewrey.events.BeerEvent;
import guru.springframework.msscbrewrey.repository.BeerRepository;
import guru.springframework.msscbrewrey.services.inventory.InventoryServiceRestClient;
import guru.springframework.msscbrewrey.web.mapper.BeerMapper;
import guru.springframework.msscbrewrey.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import static guru.springframework.msscbrewrey.config.JmsConfig.BREWING_REQUEST_Q;

@Slf4j
@RequiredArgsConstructor
@Service
public class BrewingServiceImpl implements BrewingService {

    private final BeerRepository beerRepository;
    private final InventoryServiceRestClient inventoryServiceRestClient;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

//    @Scheduled(fixedRate = 5000)
//    @Override
//    public void checkForLowInventory() {
//        log.debug("raise beer event");
//        List<Beer> beers =  beerRepository.findAll();
//        beers.stream().map(beerMapper::beerToBeerDto)
//                .peek(beer -> log.debug("Calling Inventory"))
//                .filter(beerDto ->  beerDto.getMinOnHand() >= inventoryServiceRestClient.getOnHandInventory(beerDto.getId()))
//                .map(dto -> {jmsTemplate.convertAndSend(BREWING_REQUEST_Q, dto); return new BeerEvent(dto);});
//    }

    @Scheduled(fixedRate = 5000)
    @Override
    public void checkForLowInventory() {
        log.debug("raise beer event");
        List<Beer> beers =  beerRepository.findAll();

        beers.forEach(beer -> {
            BeerDto dto = beerMapper.beerToBeerDto(beer);

            Integer onHandInv =  inventoryServiceRestClient.getOnHandInventory(beer.getId());
            log.debug("Called Inventory onHand: "+onHandInv);
            if(dto.getMinOnHand() == null || dto.getMinOnHand() >= onHandInv) {
                jmsTemplate.convertAndSend(BREWING_REQUEST_Q,new BeerEvent(dto));
            }

        });
    }
}
