package tk.shanebee.breedables.type;

/**
 * Gender options for entities
 */
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

    /** Get the name of the gender
     * @return Name of gender
     */
    public String getName() {
        return name;
    }

    /** Get the symbol of the gender
     * @return Symbol of the gender
     */
    public String getSymbol() {
        return symbol;
    }

    /** Get the pregnancy symbol of the gender
     * <p>This will be the same as the regular symbol but has a different color</p>
     * @return Pregnancy symbol of the gender
     */
    public String getPregnant() {
        return pregnant;
    }

}
