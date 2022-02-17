package guru.springframework.msscbrewrey.web.controller;

import guru.springframework.msscbrewrey.AbstractBeerBaseTest;
import guru.springframework.msscbrewrey.services.inventory.InventoryServiceRestClient;
import guru.springframework.msscbrewrey.web.mapper.BeerMapper;
import guru.springframework.msscbrewrey.web.mapper.DateMapper;
import guru.springframework.web.model.BeerDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.util.StringUtils;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import static org.springframework.restdocs.mockmvc.RestDocumentationResultHandler.*;
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "com.iyaasoft", uriPort = 80)
@WebMvcTest({BeerController.class, BeerMapper.class, DateMapper.class, InventoryServiceRestClient.class})
class BeerControllerTest extends AbstractBeerBaseTest {

    @MockBean
    private RestTemplateBuilder restTemplateBuilder;

    @Test
    void doGet() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(beerService.getBeerById(any(),anyBoolean())).thenReturn(getBeerDto());

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        this.mvc.perform(org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
                        .get("/api/v1/beer/{beerId}", uuid, false)
                        .param("showAllInventoryOnHand", "false"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Heiniken")))
                .andDo(document("v1/beer-get",
                        pathParameters(
                                parameterWithName("beerId").description("Unique identifier for a beer")
                        ),
                        requestParameters(
                                parameterWithName("showAllInventoryOnHand").description("Boolean to - decorate the response with beer on hand from inventory")
                        ),
                        getResponseFieldsSnippet(fields)));
    }

    @Test
    void doGetBeerByUpc() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(beerService.getBeerByUpc(any(),anyBoolean())).thenReturn(getBeerDto());

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        this.mvc.perform(org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
                        .get("/api/v1/beer/upc/{upc}", 12345l, false)
                        .param("showAllInventoryOnHand", "false"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Heiniken")))
                .andDo(document("v1/beer-upc",
                        pathParameters(
                                parameterWithName("upc").description("Unique product code, another unique identity for the beer for a beer")
                        ),
                        requestParameters(
                                parameterWithName("showAllInventoryOnHand").description("Boolean to - decorate the response with beer on hand from inventory")
                        ),
                        getResponseFieldsSnippet(fields)));
    }

    @Test
    void handlePost() throws Exception {
        BeerDto dto = getBeerDto();
        String json = mapper.writeValueAsString(dto);
        dto.setId(UUID.randomUUID());
        when(beerService.createBeer(any(BeerDto.class))).thenReturn(dto);

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        this.mvc.perform(org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post("/api/v1/beer")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.beerName",is("Heiniken") ))
                .andExpect(jsonPath("$.beerStyle",is("LARGER") ))
                .andDo(document("v1/beer-post"
                        ,  getRequestFieldsSnippet(fields)
                        , getResponseFieldsSnippet(fields)));
    }

    @Test
    void handlePostConstraintViolation() throws Exception {
        BeerDto dto = getBeerDto();
        dto.setPrice(null);
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

        when(beerService.updateBeer(any(UUID.class), any())).thenReturn(dto);

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        this.mvc.perform(org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put("/api/v1/beer/{beerId}" , uuid.toString())
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNoContent())
                .andDo(document("v1/beer-update",
                        pathParameters(parameterWithName("beerId").description("Unique identifier of requested beer"))
                        , getRequestFieldsSnippet(fields)));
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

    private ResponseFieldsSnippet getResponseFieldsSnippet(ConstrainedFields fields) {
        return responseFields(fields.withPath("id").description("Unique beer identifier")
                , fields.withPath("beerName").description("Name off beer")
                , fields.withPath("beerStyle").description("Type of beer")
                , fields.withPath("upc").description("Unique beer upc identifier")
                , fields.withPath("createdDate").description("Date beer created")
                , fields.withPath("lastModifiedDate").description("Date beer last modified")
                , fields.withPath("version").description("version of the beer")
                , fields.withPath("price").description("Cost of the beer to the public")
                , fields.withPath("quantityOnHand").description("Amount of beer in stock")
                , fields.withPath("minOnHand").description("Minimum amount stock level for beer ")
        );
    }

    private RequestFieldsSnippet getRequestFieldsSnippet(ConstrainedFields fields) {
        return requestFields(
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

        );
    }
}