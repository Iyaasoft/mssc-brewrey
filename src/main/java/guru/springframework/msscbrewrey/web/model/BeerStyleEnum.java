package guru.springframework.msscbrewrey.web.model;

import java.util.Arrays;
import java.util.Optional;

public enum BeerStyleEnum {
    LARGER("Larger"),
    STOUT("Stout"),
    CIDER("Cider"),
    Ale("ALE"),
    IPA("IPA"),
    PALE_ALE("Pale Ale"),
    DARK_STOUT("Dark Stout");


    private String displayName;

    BeerStyleEnum(String displayName) {
//        Optional<BeerStyleEnum> val = Arrays.stream(this.values())
//                .filter(beerStyle -> displayName.equals(beerStyle.displayName))
//                .findFirst();
        System.out.println("=========BEERENUM===============");
        System.out.println(this.name());
        System.out.println(this.getDisplayName());
        System.out.println("========================");
        this.displayName = displayName;
    }

    public String getDisplayName() { return displayName; }

    public void setDisplayName(String displayName) {
        this.displayName = displayName; }
}
