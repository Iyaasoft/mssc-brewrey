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
// @Component
public class BeerDbDataInitializer implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public static final String BEER_1_UPC = "10631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    public static final UUID BEER_1_UUID = UUID.fromString("2d2af9b0-3e1a-46e1-8f01-d7293f3e985e");
    public static final UUID BEER_2_UUID = UUID.fromString("857670c4-898b-418a-ac8c-519fb86fd1ad");
    public static final UUID BEER_3_UUID = UUID.fromString("8cbcdaa1-f66a-4dbd-a98f-03b889d4cb4e");


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
