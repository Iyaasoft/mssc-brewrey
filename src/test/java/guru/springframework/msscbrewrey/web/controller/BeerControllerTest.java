package guru.springframework.msscbrewrey.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewrey.services.BeerService;
import guru.springframework.msscbrewrey.web.mapper.BeerMapper;
import guru.springframework.msscbrewrey.web.mapper.DateMapper;
import guru.springframework.msscbrewrey.web.model.BearStyleEnum;
import guru.springframework.msscbrewrey.web.model.BeerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.io.FileDescriptor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import static org.springframework.restdocs.mockmvc.RestDocumentationResultHandler.*;
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "com.iyaasoft", uriPort = 80)
@WebMvcTest({BeerController.class, BeerMapper.class, DateMapper.class})
class BeerControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    BeerService beerService;

    @Autowired
    ObjectMapper mapper;

    @Test
    void doGet() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(beerService.getBeerById(any())).thenReturn(BeerDto.builder()
                .beerName("Heiniken")
                .beerStyle(BearStyleEnum.IPA)
                .upc(2876193L)
                .id(uuid).build());

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        this.mvc.perform(org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
                        .get("/api/v1/beer/{beerId}", uuid)
                        .param("isCold", "No"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Heiniken")))
                .andDo(document("v1/beer-get",
                        pathParameters(
                                parameterWithName("beerId").description("Unique identifier for a beer")
                        ),
                        requestParameters(
                                parameterWithName("isCold").description("Not Used, Is cold query parameter")
                        ),
                        responseFields(fields.withPath("id").description("Unique beer identifier")
                            , fields.withPath("beerName").description("Name off beer")
                            , fields.withPath("beerStyle").description("Type of beer")
                            , fields.withPath("upc").description("Unique beer upc identifier")
                            , fields.withPath("createdDate").description("Date beer created")
                            , fields.withPath("lastModifiedDate").description("Date beer last modified")
                            , fields.withPath("version").description("version of the beer")
                            , fields.withPath("price").description("Cost of the beer to the public")
                            , fields.withPath("quantityOnHand").description("Amount of beer in stock")
                            , fields.withPath("minOnHand").description("Minimum amount stock level for beer ")
                        )));
    }

    @Test
    void handlePost() throws Exception {
        BeerDto dto = getBeerDto();
        String json = mapper.writeValueAsString(dto);
        dto.setId(UUID.randomUUID());
        when(beerService.createBeer(any())).thenReturn(dto);

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        this.mvc.perform(org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post("/api/v1/beer")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andDo(document("v1/beer-post",
                        requestFields(
                                fields.withPath("id").ignored()
                                , fields.withPath("upc").description("The unique upc of the beer")
                                , fields.withPath("beerStyle").description("The type of the beer")
                                , fields.withPath("beerName").description("The name of the beer")
                                , fields.withPath("createdDate").ignored()
                                , fields.withPath("lastModifiedDate").ignored()
                                , fields.withPath("version").ignored()
                                , fields.withPath("price").description("Cost of the beer to the public")
                                , fields.withPath("quantityOnHand").description("Amount of beer in stock")
                                , fields.withPath("minOnHand").description("Minimum amount stock level for beer ")),
                        responseHeaders(headerWithName("Location").description("Unique identifier of beer just created"))
                                ));
    }

    @Test
    void handlePostConstraintViolation() throws Exception {
        BeerDto dto = BeerDto.builder()
                .beerName("Heiniken")
                .price(new BigDecimal(2.50))
                .upc(1220L).build();

        String json = mapper.writeValueAsString(dto);
        this.mvc.perform(org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post("/api/v1/beer")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest());

    }


    @Test
    void handlePut() throws Exception {
        UUID uuid = UUID.randomUUID();
        BeerDto dto = getBeerDto();

        String json = mapper.writeValueAsString(dto);

        doNothing().when(beerService).updateBeer(any(UUID.class), any());

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        this.mvc.perform(org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put("/api/v1/beer/{beerId}" , uuid.toString())
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNoContent())
                .andDo(document("v1/beer-update",
                        pathParameters(parameterWithName("beerId").description("Unique identifier of requested beer"))
                        , requestFields(
                                fields.withPath("id").ignored()
                                , fields.withPath("upc").description("The unique upc of the beer")
                                , fields.withPath("beerStyle").description("The type of the beer")
                                , fields.withPath("beerName").description("The name of the beer")
                                , fields.withPath("createdDate").ignored()
                                , fields.withPath("lastModifiedDate").ignored()
                                , fields.withPath("version").ignored()
                                , fields.withPath("price").description("Cost of the beer to the public")
                                , fields.withPath("quantityOnHand").description("Amount of beer in stock")
                                , fields.withPath("minOnHand").description("Minimum amount stock level for beer ")
                        )));
    }

    private BeerDto getBeerDto() {
        return BeerDto.builder()
                .beerName("Heiniken")
                .beerStyle(BearStyleEnum.Larger)
                .price(new BigDecimal(2.50))
                .upc(1220L)
                .minOnHand(36)
                .quantityOnHand(230).build();
    }

    @Test
    void doDelete() throws Exception {
        doNothing().when(beerService).deleteBeer(any());
        this.mvc.perform(org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete("/api/v1/beer/{beerId}" , UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("v1/beer-delete",
                        pathParameters(
                                parameterWithName("beerId").description("Unique identifier of a beer to be removed"))));
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }

}