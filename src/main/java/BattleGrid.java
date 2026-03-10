public class BattleGrid {
    // TODO: Declare fields here

    // TODO: Write the constructor

    // TODO: Write getters

    // TODO: Write placeUnit(int row, int col, Unit unit)
    //   public boolean placeUnit(int row, int col, Unit unit)
    //   - Return false (do nothing) if unit is null
    //   - Return false (do nothing) if row or col is out of bounds
    //   - Return false (do nothing) if the cell is already occupied
    //   - Otherwise: place unit at grid[row][col] and return true

    // TODO: Write getUnit(int row, int col)
    //   public Unit getUnit(int row, int col)
    //   - Return null if row or col is out of bounds
    //   - Otherwise: return grid[row][col]  (may be null if cell is empty)

    // TODO: Write countUnitsInRow(int row)
    //   public int countUnitsInRow(int row)
    //   - Return 0 if row is out of bounds
    //   - Otherwise: traverse the row; count and return non-null cells

    // TODO: Write countUnitsInCol(int col)
    //   public int countUnitsInCol(int col)
    //   - Return 0 if col is out of bounds
    //   - Otherwise: traverse the column; count and return non-null cells

    // TODO: Write strengthInRow(int row)
    //   public int strengthInRow(int row)
    //   - Return 0 if row is out of bounds
    //   - Otherwise: traverse the row; sum and return strength values of non-null units

    // TODO: Write strengthInCol(int col)
    //   public int strengthInCol(int col)
    //   - Return 0 if col is out of bounds
    //   - Otherwise: traverse the column; sum and return strength values of non-null units

    // TODO: Write getStrongestRow()
    //   public int getStrongestRow()
    //   - Return the index of the row with the highest total strength
    //   - If all rows have equal strength (including all zeros), return 0
    //   - In case of a tie, return the lowest row index

    // TODO: Write toString()
    //   public String toString()
    //   - Returns a multi-line String representing the grid
    //   - Each occupied cell shows the unit's symbol; empty cells show '.'
    //   - Cells are separated by a single space
    //   - Rows are separated by "\n"
    //   - No trailing newline at the end
    //
    //   Example (3x4 grid, units at (0,1)='A' and (2,3)='S'):
    //   . A . .
    //   . . . .
    //   . . . S
}