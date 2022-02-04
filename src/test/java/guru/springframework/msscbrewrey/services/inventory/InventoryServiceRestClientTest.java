package guru.springframework.msscbrewrey.services.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

//import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import guru.springframework.msscbrewrey.BreweryWiremockParameterResolver;
import guru.springframework.msscbrewrey.MsscBrewreyApplication;
import guru.springframework.msscbrewrey.services.inventory.model.BeerInventoryDto;
import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(BreweryWiremockParameterResolver.class)
@SpringBootTest(classes = {MsscBrewreyApplication.class})
@ContextConfiguration(initializers = { WiremockInitialiser.class })
class InventoryServiceRestClientTest {

    // NOTE this error report is an intellij thing can't stop it. test runs fine and passes
    @Autowired
    public  WireMockServer wireMockServer;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    InventoryServiceRestClient inventoryServiceRestClient;
    // wireMockRuntimeInfo provides runtime info about the wiremock environment
    @Test
    public void ShouldRetrieveOnHandQuantityFromInventoryService (WireMockRuntimeInfo wireMockRuntimeInfo) throws Exception { // ( WireMockRuntimeInfo wmRuntimeInfo) {

        List<BeerInventoryDto>  inventory = new ArrayList<>();
        BeerInventoryDto dto = BeerInventoryDto.builder().beerId(UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb")).quantityOnHand(50).build();
        inventory.add(dto);
        String responseJson =  mapper.writeValueAsString(inventory);
        wireMockServer.stubFor(get(WireMock.urlMatching("/api/v1/beer/0a818933-087d-47f2-ad83-2f986ed087eb/inventory"))
            .willReturn(aResponse().withBody(responseJson)
		    .withStatus(HttpStatus.OK.value())
            .withHeader("content-type", "application/json")));

    int result =  inventoryServiceRestClient.getOnHandInventory(UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb"));
        // not started any recording by the way
        System.out.println("Recording status : " +  wireMockRuntimeInfo.getWireMock().getStubRecordingStatus().getStatus());
        assertTrue(result == 50);
    }
}