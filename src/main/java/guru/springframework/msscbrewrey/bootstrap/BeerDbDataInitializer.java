package guru.springframework.msscbrewrey.bootstrap;

import guru.springframework.msscbrewrey.domain.Beer;
import guru.springframework.msscbrewrey.repository.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;

@Slf4j
@Component
public class BeerDbDataInitializer implements CommandLineRunner {

    private final BeerRepository beerRepository;

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
            beerRepository.save(Beer.builder().beerName("Heiniken").beerStyle("Larger").upc(Math.abs(rd.nextLong())).price(new BigDecimal(2.50)).quantityOnHand(240).build());
            beerRepository.save(Beer.builder().beerName("Guiness").beerStyle("Dark Stout").upc(Math.abs(rd.nextLong())).price(new BigDecimal(3.00)).quantityOnHand(200).build());
            beerRepository.save(Beer.builder().beerName("Stella Artoi").beerStyle("Larger").upc(Math.abs(rd.nextLong())).price(new BigDecimal(2.00)).quantityOnHand(150).build());
        }
        log.info("Bear objects in repo : "+ beerRepository.count());
    }


}
