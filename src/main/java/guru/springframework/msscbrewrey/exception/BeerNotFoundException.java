package guru.springframework.msscbrewrey.exception;

public class BeerNotFoundException extends RuntimeException {

    private static final String BEER_NOT_FOUND = "Beer not found for id given!";
    public BeerNotFoundException(){
        super(BEER_NOT_FOUND);
    }
}
