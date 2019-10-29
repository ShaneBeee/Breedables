package tk.shanebee.breedables.type;

public enum Gender {

    MALE("Male", "♂"),
    FEMALE("Female", "♀");

    private String name;
    private String symbol;

    Gender(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return name;
    }

}