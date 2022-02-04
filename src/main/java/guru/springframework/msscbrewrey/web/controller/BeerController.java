package guru.springframework.msscbrewrey.web.controller;

import guru.springframework.msscbrewrey.services.BeerService;
import guru.springframework.msscbrewrey.web.model.BeerDto;
import guru.springframework.msscbrewrey.web.model.BeerPageList;
import guru.springframework.msscbrewrey.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

    private final BeerService beerService;

    @GetMapping({"/"})
    public ResponseEntity<BeerPageList> doGetList(@RequestParam("pageNumber") Integer pageNumber,
                                                 @RequestParam("pageSize") Integer pageSize,
                                                 @RequestParam(value = "beerName", required = false) String beerName,
                                                 @RequestParam(value ="beerStyle", required = false) BeerStyleEnum beerStyle,
                                                 @RequestParam(value = "showAllInventoryOnHand", required = false) boolean showAllInventoryOnHand){

        return new ResponseEntity(beerService.getBeerList(beerName, beerStyle, PageRequest.of(pageNumber, pageSize ), showAllInventoryOnHand),HttpStatus.OK);
    }

    @GetMapping({"/{beerId}"})
    public ResponseEntity<BeerDto> doGet(@PathVariable("beerId") UUID beerId,
                                         @RequestParam(value = "showAllInventoryOnHand") boolean showAllInventoryOnHand) {
        return new ResponseEntity(beerService.getBeerById(beerId, showAllInventoryOnHand), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity handlePost(@Valid @RequestBody BeerDto beerDto) throws ConstraintViolationException{
        return new ResponseEntity(beerService.createBeer(beerDto), HttpStatus.CREATED);
    }

    @PutMapping({"/{beerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity handlePut(@PathVariable("beerId")  UUID beerId, @Valid @RequestBody BeerDto beerDto) throws ConstraintViolationException {
        return new ResponseEntity(beerService.updateBeer(beerId, beerDto),HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({"/{beerId}"})
    @ResponseStatus(HttpStatus.OK)
    public void doDelete(@PathVariable("beerId") UUID beerId) {
        beerService.deleteBeer(beerId);
    }

    @GetMapping("/upc/{upcId}")
    public ResponseEntity doGetBeerByUpc(@PathVariable("upcId") Long upcId,  @RequestParam(value = "showAllInventoryOnHand") boolean showAllInventoryOnHand ) {
        return new ResponseEntity(beerService.getBeerByUpc(upcId, showAllInventoryOnHand),HttpStatus.OK);
    }
}