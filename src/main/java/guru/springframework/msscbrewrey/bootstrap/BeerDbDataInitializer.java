package guru.springframework.msscbrewrey.bootstrap;

import guru.springframework.msscbrewrey.domain.Beer;
import guru.springframework.msscbrewrey.repository.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Component
public class BeerDbDataInitializer implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public static final String BEER_1_UPC = "10631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    public static final UUID BEER_1_UUID = UUID.fromString("38c704a0-9f4f-49ce-94dd-436f13001ca7");
    public static final UUID BEER_2_UUID = UUID.fromString("b0f2d5bc-17df-4b84-abe3-3b0913173e0f");
    public static final UUID BEER_3_UUID = UUID.fromString("34dd0cdc-6cbe-4c04-a14d-eb424efc05d6");


    public BeerDbDataInitializer(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBearData();
    }

    private void loadBearData() {
        if( beerRepository.count() == 0) {
            Random rd = new Random(); // creating Random object
            System.out.println();
            beerRepository.save( Beer.builder().id(BEER_1_UUID).beerName("Heiniken").beerStyle("LARGER").upc(BEER_1_UPC).minOnHand(65).amountToBrew(150).price(new BigDecimal(2.50)).build());
            beerRepository.save( Beer.builder().id(BEER_2_UUID).beerName("Guiness").beerStyle("DARK_STOUT").upc(BEER_2_UPC ).minOnHand(65).amountToBrew(100).price(new BigDecimal(3.00)).build());
            beerRepository.save( Beer.builder().id(BEER_3_UUID).beerName("Stella Artoi").beerStyle("IPA").upc(BEER_3_UPC ).minOnHand(65).amountToBrew(120).price(new BigDecimal(2.00)).build());
        }
        log.info("Bear objects in repo : "+ beerRepository.count());
        beerRepository.findAll().forEach(item -> System.out.println(item.getBeerName()+" ALLBEERSFOUND "+item.getId()));
    }


}
