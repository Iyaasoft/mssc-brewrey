package guru.springframework.msscbrewrey.services.scheduled;

import guru.springframework.msscbrewrey.config.JmsConfig;
import guru.springframework.msscbrewrey.domain.Beer;
import guru.springframework.msscbrewrey.events.BeerEvent;
import guru.springframework.msscbrewrey.events.BrewBeerEvent;
import guru.springframework.msscbrewrey.events.NewInventoryEvent;
import guru.springframework.msscbrewrey.exception.BeerNotFoundException;
import guru.springframework.msscbrewrey.repository.BeerRepository;
import guru.springframework.msscbrewrey.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Message;
import javax.transaction.Transactional;
import java.util.InvalidPropertiesFormatException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingListener {

    private final JmsTemplate jmsTemplate;
    private final BeerRepository beerRepository;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_Q)
    public void listen(BrewBeerEvent event) {
       BeerDto dto = event.getBeerDto();
       Optional<Beer> beer = beerRepository.findById(dto.getId());
       Integer amount = beer.orElseThrow(() -> new BeerNotFoundException()).getAmountToBrew();
       dto.setQuantityOnHand(amount);
       log.debug("Brewing beer "+ dto.getBeerName() +"  "+dto.getQuantityOnHand() );
       jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_Q, new NewInventoryEvent(dto));
    }
}
