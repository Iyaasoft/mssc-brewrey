package guru.springframework.msscbrewrey.repository;

import guru.springframework.msscbrewrey.domain.Beer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {

    Page<Beer> findByBeerNameAndBeerStyle(PageRequest pageRequest, String beerName, String toString);

    Page<Beer> findByBeerName(PageRequest pageRequest, String beerName);

    Page<Beer> findByBeerStyle(PageRequest pageRequest, String beerName);

    Beer findByUpc(Long upc);
}
