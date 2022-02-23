package guru.springframework.msscbrewrey.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

    public static final String BREWING_REQUEST_Q = "brewing-request-q";
    public static final String NEW_INVENTORY_Q = "new-inventory-q";
    public static final String VALIDATE_BEER_ORDER ="validate_order_q";
    public static final String VALIDATE_ORDER_RESULT = "validate-order-result";

    @Bean  // use spring managed object mapper to resolve date format issue
    public MessageConverter messageConverter(ObjectMapper mapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setObjectMapper(mapper);
        return converter;
    }
}
