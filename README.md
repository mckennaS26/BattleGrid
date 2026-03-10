# Battle Grid Mini Project (3 Classes)

## Objective
In this mini project, you will build a simple battle grid system like you might see in a turn-based strategy game.

You will practice:
- writing classes from a specification
- declaring instance variables (fields)
- writing constructors
- writing method headers and implementing methods
- using control structures (`if`, `else`, comparisons)
- working with **2D arrays**
- traversing rows and columns of a grid

No ArrayLists.  
No randomness.  
No input (Scanner).

---

## Files You Will Create / Complete

You will create and complete these 3 files:

- `src/main/java/Unit.java`
- `src/main/java/BattleGrid.java`
- `src/main/java/Commander.java`

---

## The Scenario

A `Commander` controls a `BattleGrid` — a rectangular grid of cells where `Unit`s can be deployed.

Each unit has:
- a name (e.g., `"Archer"`)
- a single-character symbol used for display (e.g., `'A'`)
- a strength value (e.g., `3`)

The `BattleGrid` stores units in a 2D array. It supports:
- placing units at specific positions
- checking individual cells
- scanning rows and columns to count units and calculate total strength

The `Commander` owns a grid and tracks a name and a score. The commander can deploy units and earn points based on the strength of their deployed forces.

---

# Class Requirements

## Class 1: Unit

### Fields (instance variables)
Your `Unit` class must store:
- `name` (String)
- `symbol` (char)
- `strength` (int)

### Constructor
```java
public Unit(String name, char symbol, int strength)
```

When a `Unit` is created:
- All fields are set from parameters.

### Methods
```java
public String getName()
public char getSymbol()
public int getStrength()
public String toString()
```

#### Rules
- `toString()` returns a String in this exact format:  
  `"Archer(A,3)"`  
  That is: `name + "(" + symbol + "," + strength + ")"`

---

## Class 2: BattleGrid

### Fields (instance variables)
Your `BattleGrid` class must store:
- `grid` (Unit[][]) — a 2D array of `Unit` references
- `rows` (int)
- `cols` (int)

### Constructor
```java
public BattleGrid(int rows, int cols)
```

When a `BattleGrid` is created:
- `rows` and `cols` are set from parameters.
- `grid` is initialized as a new `Unit[rows][cols]`.
- All cells start as `null` (empty).

### Methods
```java
public int getRows()
public int getCols()
public boolean placeUnit(int row, int col, Unit unit)
public Unit getUnit(int row, int col)
public int countUnitsInRow(int row)
public int countUnitsInCol(int col)
public int strengthInRow(int row)
public int strengthInCol(int col)
public int getStrongestRow()
```

#### `placeUnit` Rules
- If `unit` is `null`, return `false` and do nothing.
- If `row` or `col` is out of bounds, return `false` and do nothing.
- If the cell is already occupied (not `null`), return `false` and do nothing.
- Otherwise, place the unit at `grid[row][col]` and return `true`.

#### `getUnit` Rules
- If `row` or `col` is out of bounds, return `null`.
- Otherwise, return `grid[row][col]` (which may itself be `null` if the cell is empty).

#### `countUnitsInRow` Rules
- Returns the number of non-`null` cells in the given row.
- If `row` is out of bounds, return `0`.

#### `countUnitsInCol` Rules
- Returns the number of non-`null` cells in the given column.
- If `col` is out of bounds, return `0`.

#### `strengthInRow` Rules
- Returns the **sum of strength values** of all units in the given row.
- Empty cells (`null`) contribute `0`.
- If `row` is out of bounds, return `0`.

#### `strengthInCol` Rules
- Returns the **sum of strength values** of all units in the given column.
- Empty cells (`null`) contribute `0`.
- If `col` is out of bounds, return `0`.

#### `getStrongestRow` Rules
- Returns the **index** of the row with the highest total strength.
- If all rows have equal strength (including all zeros), return `0`.
- If there is a tie, return the **lowest** row index.

---

## Class 3: Commander

### Fields (instance variables)
Your `Commander` class must store:
- `name` (String)
- `grid` (BattleGrid)
- `score` (int)

### Constructor
```java
public Commander(String name, int rows, int cols)
```

When a `Commander` is created:
- `name` is set from the parameter.
- `grid` is initialized as a new `BattleGrid(rows, cols)`.
- `score` starts at `0`.

### Methods
```java
public String getName()
public BattleGrid getGrid()
public int getScore()
public boolean deploy(int row, int col, Unit unit)
public int getTotalStrength()
public int getTotalUnits()
public String getStatus()
```

#### `deploy` Rules
- Attempts to place `unit` at `(row, col)` on the grid.
- If placement succeeds (i.e., `grid.placeUnit(...)` returns `true`):
  - Add the unit's strength to `score`.
  - Return `true`.
- If placement fails, return `false`. Do NOT change `score`.

#### `getTotalStrength` Rules
- Returns the **sum of strength values** across **all rows** of the grid.
- Hint: loop through each row and use `strengthInRow`.

#### `getTotalUnits` Rules
- Returns the **total number of units** placed on the grid.
- Hint: loop through each row and use `countUnitsInRow`.

#### `getStatus` Rules
- Returns a String in this exact format:
  ```
  Commander: <name> | Units: <totalUnits> | Strength: <totalStrength> | Score: <score>
  ```
  Example:
  ```
  Commander: Zara | Units: 3 | Strength: 11 | Score: 11
  ```

---

## Skeleton Code

### Unit.java
```java
public class Unit {
    // TODO: Declare fields here

    // TODO: Write the constructor

    // TODO: Write getters

    // TODO: Write toString()
}
```

### BattleGrid.java
```java
public class BattleGrid {
    // TODO: Declare fields here

    // TODO: Write the constructor

    // TODO: Write getters (getRows, getCols)

    // TODO: Write placeUnit(int row, int col, Unit unit)

    // TODO: Write getUnit(int row, int col)

    // TODO: Write countUnitsInRow(int row)

    // TODO: Write countUnitsInCol(int col)

    // TODO: Write strengthInRow(int row)

    // TODO: Write strengthInCol(int col)

    // TODO: Write getStrongestRow()
}
```

### Commander.java
```java
public class Commander {
    // TODO: Declare fields here

    // TODO: Write the constructor

    // TODO: Write getters (getName, getGrid, getScore)

    // TODO: Write deploy(int row, int col, Unit unit)

    // TODO: Write getTotalStrength()

    // TODO: Write getTotalUnits()

    // TODO: Write getStatus()
}
```

---

## Notes
- Follow the method names, parameter lists, and return types exactly — your code will be tested automatically.
- All fields must be `private`.
- All constructors and methods must be `public`.
- When building Strings in `getStatus()`, do **not** use `System.out.println`. Build and return the String directly.
- A cell is **empty** if it holds `null`. An occupied cell holds a `Unit` reference.