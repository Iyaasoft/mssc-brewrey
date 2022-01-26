package guru.springframework.msscbrewrey.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewrey.services.BeerService;
import guru.springframework.msscbrewrey.web.model.BearStyleEnum;
import guru.springframework.msscbrewrey.web.model.BeerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    BeerService beerService;

    @Autowired
    ObjectMapper mapper;

    @Test
    void doGet() throws Exception {
        when(beerService.getBeerById(any())).thenReturn(BeerDto.builder().beerName("Heiniken").build());
        this.mvc.perform(get("/api/v1/beer/" + UUID.randomUUID())).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Heiniken")));
    }

    @Test
    void handlePost() throws Exception {
        BeerDto dto = BeerDto.builder()
                .beerName("Heiniken")
                .beerStyle(BearStyleEnum.Larger)
                .price(new BigDecimal(2.50))
                .upc(1220L).build();

        String json = mapper.writeValueAsString(dto);
        UUID uuid = UUID.randomUUID();
        when(beerService.createBeer(any())).thenReturn(BeerDto.builder()
                .id(uuid)
                .beerName("Heiniken")
                .price(new BigDecimal(2.50))
                .upc(1220L).build());

        this.mvc.perform(post("/api/v1/beer")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

    }

    @Test
    void handlePostConstraintViolation() throws Exception {
        BeerDto dto = BeerDto.builder()
                .beerName("Heiniken")
                .price(new BigDecimal(2.50))
                .upc(1220L).build();

        String json = mapper.writeValueAsString(dto);
        this.mvc.perform(post("/api/v1/beer")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest());


    }


    @Test
    void handlePut() throws Exception {

        BeerDto dto = BeerDto.builder()
                .beerName("Heiniken")
                .beerStyle(BearStyleEnum.Larger)
                .price(new BigDecimal(2.50))
                .upc(1220L).build();

        String json = mapper.writeValueAsString(dto);
        UUID uuid = UUID.randomUUID();
        doNothing().when(beerService).updateBeer(any(UUID.class), any());


        this.mvc.perform(put("/api/v1/beer/" + uuid.toString())
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    void doDelete() throws Exception {
        doNothing().when(beerService).deleteBeer(any());
        this.mvc.perform(delete("/api/v1/beer/" + UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}