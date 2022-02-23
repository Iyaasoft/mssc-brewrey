package guru.springframework.msscbrewrey.services.validate;

import guru.springframework.msscbrewrey.config.JmsConfig;
import guru.springframework.msscbrewrey.domain.Beer;
import guru.springframework.msscbrewrey.repository.BeerRepository;
import guru.springframework.services.validate.BeerOrderValidationResult;
import guru.springframework.web.model.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@AllArgsConstructor
@Component
public class ValidateBeerOrder  {

    private final JmsTemplate jmsTemplate;
    private final BeerRepository beerRepository;

    @Transactional
    @JmsListener(destination = JmsConfig.VALIDATE_BEER_ORDER)
    public void validateBeerMessage(@Payload BeerOrderDto beerOrderMsg, @Headers MessageHeaders headers, Message message) {
        final AtomicInteger validLines = new AtomicInteger(0);
        beerOrderMsg.getBeerOrderLines().forEach(line ->  {
            Beer beer = beerRepository.findByUpc(line.getUpc());
                    if(Objects.isNull(beer)) {
                        validLines.getAndIncrement();
                    }
        });
        BeerOrderValidationResult validationResult = BeerOrderValidationResult.builder()
                .beerOrderId(beerOrderMsg.getId())
                .isValid(validLines.get() > 0 ? false : true).build();
        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESULT, validationResult);
    }
}
