public class Commander {
    // TODO: Declare fields here
    private String name;
    private BattleGrid grid;
    private int score;

    // TODO: Write the constructor
    public Commander(String name, int rows, int cols){
        this.name = name;
        grid = new BattleGrid(rows, cols);
        score = 0;
    }
    //   - Set name from parameter
    //   - Initialize grid as new BattleGrid(rows, cols)
    //   - score starts at 0

    // TODO: Write getters
    public String getName() {
        return name;
    }
    public BattleGrid getGrid() {
        return grid;
    }
    public int getScore() {
        return score;
    }

    // TODO: Write deploy(int row, int col, Unit unit)
    public boolean deploy(int row, int col, Unit unit) {

    }
    //   - Attempt to place unit on the grid via grid.placeUnit(row, col, unit)
    //   - If placement succeeds: add the unit's strength to score, return true
    //   - If placement fails: do NOT change score, return false

    // TODO: Write getTotalStrength()
    public int getTotalStrength() {

    }
    //   - Return the sum of strength values across ALL rows of the grid
    //   - Hint: loop through each row index and use grid.strengthInRow(row)

    // TODO: Write getTotalUnits()
    public int getTotalUnits() {

    }
    //   - Return the total number of units placed on the grid
    //   - Hint: loop through each row index and use grid.countUnitsInRow(row)

    // TODO: Write getStatus()
    public String getStatus() {
        return "Commander: " + name + " | Units: " + <totalUnits> + " | Strength: " + <totalStrength> + " | Score: " + <score>
    }
    //   - Returns a single-line String in this exact format:
    //     "Commander: Zara | Units: 3 | Strength: 11 | Score: 11"
}