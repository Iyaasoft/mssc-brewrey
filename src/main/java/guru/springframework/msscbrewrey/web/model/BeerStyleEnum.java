package guru.springframework.msscbrewrey.web.model;

public enum BeerStyleEnum {
    Larger("Larger"),
    Stout("Stout"),
    Cider("Cider"),
    Ale("ALE"),
    IPA("IPA"),
    Dark_Stout("Dark Stout");


    private String displayName;

    BeerStyleEnum(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() { return displayName; }
}
