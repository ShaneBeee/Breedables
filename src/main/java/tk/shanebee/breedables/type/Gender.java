package tk.shanebee.breedables.type;

public enum Gender {

    MALE("Male", "&b♂"),
    FEMALE("Female", "&5♀", "&6♀");

    private String name;
    private String symbol;
    private String pregnant;

    Gender(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    Gender(String name, String symbol, String pregnant) {
        this(name, symbol);
        this.pregnant = pregnant;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getPregnant() {
        return pregnant;
    }

    @Override
    public String toString() {
        return name;
    }

}
