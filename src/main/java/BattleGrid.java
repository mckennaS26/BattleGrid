public class BattleGrid {
    // TODO: Declare fields here
    private Unit[][] grid;
    private int rows;
    private int cols;

    // TODO: Write the constructor
    public BattleGrid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new Unit[rows][cols];

    }
    //   - Set rows and cols from parameters
    //   - Initialize grid as new Unit[rows][cols]
    //   - All cells start as null (empty) automatically

    // TODO: Write getters
    public int getRows() {
        return rows;
    }
    public int getCols() {
        return cols;
    }

    // TODO: Write placeUnit(int row, int col, Unit unit)
    public boolean placeUnit(int row, int col, Unit unit) {
        if(unit == null) {
            return false;
        }
        else if(row > rows || col > cols) {
            return false;
        }
        else if(grid[row][col] != null) {
            return false;
        }
        else {
            grid[row][col] = unit;
            return true;
        }
    }
    //   - Return false (do nothing) if unit is null
    //   - Return false (do nothing) if row or col is out of bounds
    //   - Return false (do nothing) if the cell is already occupied
    //   - Otherwise: place unit at grid[row][col] and return true

    // TODO: Write getUnit(int row, int col)
    public Unit getUnit(int row, int col) {
        if(row > rows || col > cols) {
            return null;
        }else{
            return grid[row][col];
        }
    }
    //   - Return null if row or col is out of bounds
    //   - Otherwise: return grid[row][col]  (may be null if cell is empty)

    // TODO: Write countUnitsInRow(int row)
    public int countUnitsInRow(int row) {
       int notNull = 0;
        if (row > rows) {
            return 0;
        }else{
            for () {
                if(grid[row][col] != null) {
                    notNull++;
                }
            }
            return notNull;
        }
    }
    //   - Return 0 if row is out of bounds
    //   - Otherwise: traverse the row; count and return non-null cells

    // TODO: Write countUnitsInCol(int col)
    public int countUnitsInCol(int col) {
        int notNull = 0;
        if(col > cols) {
            return 0;
        }else {
            for() {
                for() {
                    if(grid[row][col] != null) {
                        notNull++;
                    }
                }
            }
            return notNull;
        }
    }
    //   - Return 0 if col is out of bounds
    //   - Otherwise: traverse the column; count and return non-null cells
    // /m col is constant from beginning of loop, because in a col you are gowing down by the row of each

    // TODO: Write strengthInRow(int row)
    public int strengthInRow(int row) {
        int sum = 0;
        if(row > rows) {
            return 0;
        }else{
            for() {
                sum += grid[row][col].getStrength();
            }
            return sum;
        }
    }
    //   - Return 0 if row is out of bounds
    //   - Otherwise: traverse the row; sum and return strength values of non-null units

    // TODO: Write strengthInCol(int col)
    public int strengthInCol(int col) {
       int sum = 0;
        if(col > cols) {
            return 0;
        }else {
            for() {
                sum += grid[row][col].getStrength();
            }
            return sum;
        }
    }
    //   - Return 0 if col is out of bounds
    //   - Otherwise: traverse the column; sum and return strength values of non-null units

    // TODO: Write getStrongestRow()
    public int getStrongestRow()
    //   - Return the index of the row with the highest total strength
    //   - If all rows have equal strength (including all zeros), return 0
    //   - In case of a tie, return the lowest row index
}