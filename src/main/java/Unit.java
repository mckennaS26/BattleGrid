public class Unit {
    // TODO: Declare fields here
    private String name;
    private char symbol;
    private int strength;

    // TODO: Write the constructor
    public Unit(String name, char symbol, int strength) {
        this.name = name;
        this.symbol = symbol;
        this.strength = strength;
    }
    //   - Set all fields from parameters

    // TODO: Write getters
    public String getName() {
        return name;
    }
    public char getSymbol() {
        return symbol;
    }
    public int getStrength() {
        return strength;
    }

    // TODO: Write toString()
    public String toString() {
        return name + "(" + symbol + "," + strength + ")";
    }
    //   Returns: "Archer(A,3)"
    //   Format:  name + "(" + symbol + "," + strength + ")"
}