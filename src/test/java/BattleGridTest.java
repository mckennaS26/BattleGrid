import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BattleGridTest {

    // ============================================================
    // Reflection helpers
    // ============================================================

    private static Class<?> requireClass(String simpleName) {
        try {
            return Class.forName(simpleName);
        } catch (ClassNotFoundException e) {
            fail("Missing class: " + simpleName + ". Create " + simpleName + ".java in src/main/java (default package).");
            return null;
        }
    }

    private static Field requireField(Class<?> clazz, String fieldName, Class<?> fieldType) {
        try {
            Field f = clazz.getDeclaredField(fieldName);
            assertEquals(fieldType, f.getType(),
                    clazz.getSimpleName() + " field '" + fieldName + "' must be type " + fieldType.getSimpleName() + ".");
            assertTrue(Modifier.isPrivate(f.getModifiers()),
                    clazz.getSimpleName() + " field '" + fieldName + "' should be private.");
            return f;
        } catch (NoSuchFieldException e) {
            fail(clazz.getSimpleName() + " is missing field: private " + fieldType.getSimpleName() + " " + fieldName + ";");
            return null;
        }
    }

    private static Constructor<?> requireConstructor(Class<?> clazz, Class<?>... paramTypes) {
        try {
            Constructor<?> c = clazz.getDeclaredConstructor(paramTypes);
            assertTrue(Modifier.isPublic(c.getModifiers()),
                    clazz.getSimpleName() + " constructor must be public.");
            return c;
        } catch (NoSuchMethodException e) {
            fail(clazz.getSimpleName() + " is missing constructor: public " + clazz.getSimpleName()
                    + "(" + paramList(paramTypes) + ")");
            return null;
        }
    }

    private static Method requireMethod(Class<?> clazz, String name, Class<?> returnType, Class<?>... paramTypes) {
        try {
            Method m = clazz.getDeclaredMethod(name, paramTypes);
            assertTrue(Modifier.isPublic(m.getModifiers()),
                    clazz.getSimpleName() + " method must be public: " + signature(clazz, name, returnType, paramTypes));
            assertEquals(returnType, m.getReturnType(),
                    clazz.getSimpleName() + " method has wrong return type: " + signature(clazz, name, returnType, paramTypes));
            return m;
        } catch (NoSuchMethodException e) {
            fail(clazz.getSimpleName() + " is missing method: " + signature(clazz, name, returnType, paramTypes));
            return null;
        }
    }

    private static String signature(Class<?> clazz, String name, Class<?> returnType, Class<?>... params) {
        return "public " + returnType.getSimpleName() + " " + name + "(" + paramList(params) + ")";
    }

    private static String paramList(Class<?>... params) {
        if (params == null || params.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(params[i].getSimpleName());
        }
        return sb.toString();
    }

    private static Object newInstance(Constructor<?> ctor, Object... args) {
        try {
            return ctor.newInstance(args);
        } catch (Exception e) {
            fail("Constructor threw an exception: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            return null;
        }
    }

    private static Object invoke(Method m, Object target, Object... args) {
        try {
            return m.invoke(target, args);
        } catch (Exception e) {
            Throwable cause = (e.getCause() != null) ? e.getCause() : e;
            fail("Method call failed: " + m.getName() + " -> " + cause.getClass().getSimpleName() + ": " + cause.getMessage());
            return null;
        }
    }

    // ============================================================
    // Signature + field tests
    // ============================================================

    @Test
    public void unit_requiredFields_constructor_and_methods_exist() {
        System.out.println("\n=== Signature Check: Unit ===");

        Class<?> unit = requireClass("Unit");

        requireField(unit, "name", String.class);
        requireField(unit, "symbol", char.class);
        requireField(unit, "strength", int.class);

        requireConstructor(unit, String.class, char.class, int.class);

        requireMethod(unit, "getName", String.class);
        requireMethod(unit, "getSymbol", char.class);
        requireMethod(unit, "getStrength", int.class);
        requireMethod(unit, "toString", String.class);
    }

    @Test
    public void battleGrid_requiredFields_constructor_and_methods_exist() {
        System.out.println("\n=== Signature Check: BattleGrid ===");

        Class<?> grid = requireClass("BattleGrid");
        Class<?> unit = requireClass("Unit");

        requireField(grid, "grid", Unit[][].class);
        requireField(grid, "rows", int.class);
        requireField(grid, "cols", int.class);

        requireConstructor(grid, int.class, int.class);

        requireMethod(grid, "getRows", int.class);
        requireMethod(grid, "getCols", int.class);
        requireMethod(grid, "placeUnit", boolean.class, int.class, int.class, unit);
        requireMethod(grid, "getUnit", unit, int.class, int.class);
        requireMethod(grid, "countUnitsInRow", int.class, int.class);
        requireMethod(grid, "countUnitsInCol", int.class, int.class);
        requireMethod(grid, "strengthInRow", int.class, int.class);
        requireMethod(grid, "strengthInCol", int.class, int.class);
        requireMethod(grid, "getStrongestRow", int.class);
        requireMethod(grid, "toString", String.class);
    }

    @Test
    public void commander_requiredFields_constructor_and_methods_exist() {
        System.out.println("\n=== Signature Check: Commander ===");

        Class<?> commander = requireClass("Commander");
        Class<?> grid      = requireClass("BattleGrid");
        Class<?> unit      = requireClass("Unit");

        requireField(commander, "name", String.class);
        requireField(commander, "grid", grid);
        requireField(commander, "score", int.class);

        requireConstructor(commander, String.class, int.class, int.class);

        requireMethod(commander, "getName", String.class);
        requireMethod(commander, "getGrid", grid);
        requireMethod(commander, "getScore", int.class);
        requireMethod(commander, "deploy", boolean.class, int.class, int.class, unit);
        requireMethod(commander, "getTotalStrength", int.class);
        requireMethod(commander, "getTotalUnits", int.class);
        requireMethod(commander, "getStatus", String.class);
    }

    // ============================================================
    // Behavior tests
    // ============================================================

    @Test
    public void unit_behavior_constructor_getters_and_toString() {
        System.out.println("\n=== Behavior: Unit ===");

        Class<?> unit = requireClass("Unit");
        Constructor<?> ctor = requireConstructor(unit, String.class, char.class, int.class);
        Method getName     = requireMethod(unit, "getName", String.class);
        Method getSymbol   = requireMethod(unit, "getSymbol", char.class);
        Method getStrength = requireMethod(unit, "getStrength", int.class);
        Method toString    = requireMethod(unit, "toString", String.class);

        Object archer = newInstance(ctor, "Archer", 'A', 3);

        assertEquals("Archer", invoke(getName, archer),
                "Unit.getName() should return the name passed to the constructor.");
        assertEquals('A', invoke(getSymbol, archer),
                "Unit.getSymbol() should return the symbol passed to the constructor.");
        assertEquals(3, (int) invoke(getStrength, archer),
                "Unit.getStrength() should return the strength passed to the constructor.");
        assertEquals("Archer(A,3)", invoke(toString, archer),
                "Unit.toString() should return \"Archer(A,3)\" for name=Archer, symbol=A, strength=3.");

        Object scout = newInstance(ctor, "Scout", 'S', 1);
        assertEquals("Scout(S,1)", invoke(toString, scout),
                "Unit.toString() should format correctly for other units.");
    }

    @Test
    public void battleGrid_behavior_constructor_and_dimensions() {
        System.out.println("\n=== Behavior: BattleGrid constructor and dimensions ===");

        Class<?> grid = requireClass("BattleGrid");
        Constructor<?> ctor = requireConstructor(grid, int.class, int.class);
        Method getRows = requireMethod(grid, "getRows", int.class);
        Method getCols = requireMethod(grid, "getCols", int.class);

        Object g = newInstance(ctor, 3, 4);

        assertEquals(3, (int) invoke(getRows, g),
                "BattleGrid.getRows() should return the row count passed to the constructor.");
        assertEquals(4, (int) invoke(getCols, g),
                "BattleGrid.getCols() should return the col count passed to the constructor.");
    }

    @Test
    public void battleGrid_behavior_placeUnit_and_getUnit() {
        System.out.println("\n=== Behavior: BattleGrid.placeUnit and getUnit ===");

        Class<?> gridClass = requireClass("BattleGrid");
        Class<?> unitClass = requireClass("Unit");

        Constructor<?> gridCtor = requireConstructor(gridClass, int.class, int.class);
        Constructor<?> unitCtor = requireConstructor(unitClass, String.class, char.class, int.class);

        Method placeUnit = requireMethod(gridClass, "placeUnit", boolean.class, int.class, int.class, unitClass);
        Method getUnit   = requireMethod(gridClass, "getUnit", unitClass, int.class, int.class);

        Object g = newInstance(gridCtor, 3, 4);
        Object archer = newInstance(unitCtor, "Archer", 'A', 3);
        Object scout  = newInstance(unitCtor, "Scout",  'S', 1);

        // Successful placement
        assertTrue((boolean) invoke(placeUnit, g, 0, 1, archer),
                "placeUnit should return true when placing a unit in an empty, in-bounds cell.");

        // getUnit returns the placed unit
        assertSame(archer, invoke(getUnit, g, 0, 1),
                "getUnit(0, 1) should return the unit that was placed there.");

        // Empty cell returns null
        assertNull(invoke(getUnit, g, 1, 1),
                "getUnit on an empty cell should return null.");

        // Cannot place in an already-occupied cell
        assertFalse((boolean) invoke(placeUnit, g, 0, 1, scout),
                "placeUnit should return false when the cell is already occupied.");
        assertSame(archer, invoke(getUnit, g, 0, 1),
                "The original unit should remain after a failed placeUnit attempt.");

        // Cannot place a null unit
        assertFalse((boolean) invoke(placeUnit, g, 1, 0, (Object) null),
                "placeUnit should return false when unit is null.");

        // Out-of-bounds placement
        assertFalse((boolean) invoke(placeUnit, g, -1, 0, scout),
                "placeUnit should return false for negative row.");
        assertFalse((boolean) invoke(placeUnit, g, 0, 10, scout),
                "placeUnit should return false for col beyond grid width.");
        assertFalse((boolean) invoke(placeUnit, g, 5, 0, scout),
                "placeUnit should return false for row beyond grid height.");

        // Out-of-bounds getUnit
        assertNull(invoke(getUnit, g, -1, 0),
                "getUnit should return null for negative row.");
        assertNull(invoke(getUnit, g, 0, 99),
                "getUnit should return null for col beyond grid width.");
    }

    @Test
    public void battleGrid_behavior_countUnitsInRow_and_Col() {
        System.out.println("\n=== Behavior: BattleGrid.countUnitsInRow and countUnitsInCol ===");

        Class<?> gridClass = requireClass("BattleGrid");
        Class<?> unitClass = requireClass("Unit");

        Constructor<?> gridCtor = requireConstructor(gridClass, int.class, int.class);
        Constructor<?> unitCtor = requireConstructor(unitClass, String.class, char.class, int.class);

        Method placeUnit        = requireMethod(gridClass, "placeUnit", boolean.class, int.class, int.class, unitClass);
        Method countUnitsInRow  = requireMethod(gridClass, "countUnitsInRow", int.class, int.class);
        Method countUnitsInCol  = requireMethod(gridClass, "countUnitsInCol", int.class, int.class);

        Object g = newInstance(gridCtor, 3, 4);
        Object a = newInstance(unitCtor, "Archer", 'A', 3);
        Object b = newInstance(unitCtor, "Brute",  'B', 5);
        Object s = newInstance(unitCtor, "Scout",  'S', 1);

        // Place units: row 0 col 0, row 0 col 2, row 2 col 2
        invoke(placeUnit, g, 0, 0, a);
        invoke(placeUnit, g, 0, 2, b);
        invoke(placeUnit, g, 2, 2, s);

        assertEquals(2, (int) invoke(countUnitsInRow, g, 0),
                "countUnitsInRow(0) should return 2 (two units in row 0).");
        assertEquals(0, (int) invoke(countUnitsInRow, g, 1),
                "countUnitsInRow(1) should return 0 (row 1 is empty).");
        assertEquals(1, (int) invoke(countUnitsInRow, g, 2),
                "countUnitsInRow(2) should return 1 (one unit in row 2).");

        assertEquals(1, (int) invoke(countUnitsInCol, g, 0),
                "countUnitsInCol(0) should return 1 (one unit in col 0).");
        assertEquals(0, (int) invoke(countUnitsInCol, g, 1),
                "countUnitsInCol(1) should return 0 (col 1 is empty).");
        assertEquals(2, (int) invoke(countUnitsInCol, g, 2),
                "countUnitsInCol(2) should return 2 (two units in col 2).");
        assertEquals(0, (int) invoke(countUnitsInCol, g, 3),
                "countUnitsInCol(3) should return 0 (col 3 is empty).");

        // Out-of-bounds
        assertEquals(0, (int) invoke(countUnitsInRow, g, -1),
                "countUnitsInRow should return 0 for out-of-bounds row.");
        assertEquals(0, (int) invoke(countUnitsInCol, g, 99),
                "countUnitsInCol should return 0 for out-of-bounds col.");
    }

    @Test
    public void battleGrid_behavior_strengthInRow_and_Col() {
        System.out.println("\n=== Behavior: BattleGrid.strengthInRow and strengthInCol ===");

        Class<?> gridClass = requireClass("BattleGrid");
        Class<?> unitClass = requireClass("Unit");

        Constructor<?> gridCtor = requireConstructor(gridClass, int.class, int.class);
        Constructor<?> unitCtor = requireConstructor(unitClass, String.class, char.class, int.class);

        Method placeUnit     = requireMethod(gridClass, "placeUnit", boolean.class, int.class, int.class, unitClass);
        Method strengthInRow = requireMethod(gridClass, "strengthInRow", int.class, int.class);
        Method strengthInCol = requireMethod(gridClass, "strengthInCol", int.class, int.class);

        Object g = newInstance(gridCtor, 3, 4);
        Object a = newInstance(unitCtor, "Archer", 'A', 3);
        Object b = newInstance(unitCtor, "Brute",  'B', 5);
        Object s = newInstance(unitCtor, "Scout",  'S', 1);

        // Place: (0,0)=3, (0,2)=5, (2,2)=1
        invoke(placeUnit, g, 0, 0, a);
        invoke(placeUnit, g, 0, 2, b);
        invoke(placeUnit, g, 2, 2, s);

        assertEquals(8, (int) invoke(strengthInRow, g, 0),
                "strengthInRow(0) should return 8 (3 + 5).");
        assertEquals(0, (int) invoke(strengthInRow, g, 1),
                "strengthInRow(1) should return 0 (empty row).");
        assertEquals(1, (int) invoke(strengthInRow, g, 2),
                "strengthInRow(2) should return 1 (Scout strength).");

        assertEquals(3, (int) invoke(strengthInCol, g, 0),
                "strengthInCol(0) should return 3 (Archer strength).");
        assertEquals(0, (int) invoke(strengthInCol, g, 1),
                "strengthInCol(1) should return 0 (empty col).");
        assertEquals(6, (int) invoke(strengthInCol, g, 2),
                "strengthInCol(2) should return 6 (5 + 1).");

        // Out-of-bounds
        assertEquals(0, (int) invoke(strengthInRow, g, -1),
                "strengthInRow should return 0 for out-of-bounds row.");
        assertEquals(0, (int) invoke(strengthInCol, g, 99),
                "strengthInCol should return 0 for out-of-bounds col.");
    }

    @Test
    public void battleGrid_behavior_getStrongestRow() {
        System.out.println("\n=== Behavior: BattleGrid.getStrongestRow ===");

        Class<?> gridClass = requireClass("BattleGrid");
        Class<?> unitClass = requireClass("Unit");

        Constructor<?> gridCtor = requireConstructor(gridClass, int.class, int.class);
        Constructor<?> unitCtor = requireConstructor(unitClass, String.class, char.class, int.class);

        Method placeUnit       = requireMethod(gridClass, "placeUnit", boolean.class, int.class, int.class, unitClass);
        Method getStrongestRow = requireMethod(gridClass, "getStrongestRow", int.class);

        // Empty grid: all rows have strength 0 → return 0
        Object emptyGrid = newInstance(gridCtor, 3, 3);
        assertEquals(0, (int) invoke(getStrongestRow, emptyGrid),
                "getStrongestRow on an empty grid should return 0 (all rows tied at 0).");

        // Build a grid where row 2 is strongest
        Object g = newInstance(gridCtor, 3, 4);
        Object a = newInstance(unitCtor, "Archer", 'A', 2);
        Object b = newInstance(unitCtor, "Brute",  'B', 5);
        Object s = newInstance(unitCtor, "Scout",  'S', 7);
        Object k = newInstance(unitCtor, "Knight", 'K', 4);

        invoke(placeUnit, g, 0, 0, a);  // row 0 strength = 2
        invoke(placeUnit, g, 1, 1, b);  // row 1 strength = 5
        invoke(placeUnit, g, 2, 0, s);  // row 2 strength = 7 + 4 = 11
        invoke(placeUnit, g, 2, 3, k);

        assertEquals(2, (int) invoke(getStrongestRow, g),
                "getStrongestRow should return 2 when row 2 has the highest total strength.");

        // Tie: rows 0 and 1 both have strength 5 → return lowest index (0)
        Object tieGrid = newInstance(gridCtor, 2, 3);
        Object x = newInstance(unitCtor, "X", 'X', 5);
        Object y = newInstance(unitCtor, "Y", 'Y', 5);
        invoke(placeUnit, tieGrid, 0, 0, x);
        invoke(placeUnit, tieGrid, 1, 0, y);

        assertEquals(0, (int) invoke(getStrongestRow, tieGrid),
                "getStrongestRow should return the lowest index when rows are tied.");
    }

    @Test
    public void battleGrid_behavior_toString() {
        System.out.println("\n=== Behavior: BattleGrid.toString ===");

        Class<?> gridClass = requireClass("BattleGrid");
        Class<?> unitClass = requireClass("Unit");

        Constructor<?> gridCtor = requireConstructor(gridClass, int.class, int.class);
        Constructor<?> unitCtor = requireConstructor(unitClass, String.class, char.class, int.class);

        Method placeUnit = requireMethod(gridClass, "placeUnit", boolean.class, int.class, int.class, unitClass);
        Method toString  = requireMethod(gridClass, "toString", String.class);

        // Empty 2x3 grid
        Object g1 = newInstance(gridCtor, 2, 3);
        assertEquals(". . .\n. . .", invoke(toString, g1),
                "Empty 2x3 grid should display all dots with no trailing newline.");

        // 3x4 grid with units at (0,1)='A' and (2,3)='S'
        Object g2 = newInstance(gridCtor, 3, 4);
        Object archer = newInstance(unitCtor, "Archer", 'A', 3);
        Object scout  = newInstance(unitCtor, "Scout",  'S', 1);
        invoke(placeUnit, g2, 0, 1, archer);
        invoke(placeUnit, g2, 2, 3, scout);

        String expected = ". A . .\n. . . .\n. . . S";
        assertEquals(expected, invoke(toString, g2),
                "BattleGrid.toString() should show unit symbols at occupied cells and '.' elsewhere.");
    }

    @Test
    public void commander_behavior_constructor_deploy_and_score() {
        System.out.println("\n=== Behavior: Commander constructor, deploy, and score ===");

        Class<?> cmdClass  = requireClass("Commander");
        Class<?> gridClass = requireClass("BattleGrid");
        Class<?> unitClass = requireClass("Unit");

        Constructor<?> cmdCtor  = requireConstructor(cmdClass, String.class, int.class, int.class);
        Constructor<?> unitCtor = requireConstructor(unitClass, String.class, char.class, int.class);

        Method getName   = requireMethod(cmdClass, "getName", String.class);
        Method getGrid   = requireMethod(cmdClass, "getGrid", gridClass);
        Method getScore  = requireMethod(cmdClass, "getScore", int.class);
        Method deploy    = requireMethod(cmdClass, "deploy", boolean.class, int.class, int.class, unitClass);

        Object zara = newInstance(cmdCtor, "Zara", 3, 4);

        assertEquals("Zara", invoke(getName, zara),
                "Commander.getName() should return the name passed to the constructor.");
        assertNotNull(invoke(getGrid, zara),
                "Commander.getGrid() should not return null after construction.");
        assertEquals(0, (int) invoke(getScore, zara),
                "Commander.getScore() should start at 0.");

        Object archer = newInstance(unitCtor, "Archer", 'A', 3);
        Object knight = newInstance(unitCtor, "Knight", 'K', 4);
        Object scout  = newInstance(unitCtor, "Scout",  'S', 1);

        assertTrue((boolean) invoke(deploy, zara, 0, 0, archer),
                "deploy should return true when placement succeeds.");
        assertEquals(3, (int) invoke(getScore, zara),
                "Score should increase by unit strength after a successful deploy.");

        assertTrue((boolean) invoke(deploy, zara, 1, 2, knight),
                "deploy should return true for a second valid placement.");
        assertEquals(7, (int) invoke(getScore, zara),
                "Score should accumulate across successful deploys (3 + 4 = 7).");

        // Duplicate placement should fail and NOT change score
        assertFalse((boolean) invoke(deploy, zara, 0, 0, scout),
                "deploy should return false when the cell is already occupied.");
        assertEquals(7, (int) invoke(getScore, zara),
                "Score should NOT change after a failed deploy.");
    }

    @Test
    public void commander_behavior_getTotalStrength_getTotalUnits_getStatus() {
        System.out.println("\n=== Behavior: Commander.getTotalStrength, getTotalUnits, getStatus ===");

        Class<?> cmdClass  = requireClass("Commander");
        Class<?> unitClass = requireClass("Unit");

        Constructor<?> cmdCtor  = requireConstructor(cmdClass, String.class, int.class, int.class);
        Constructor<?> unitCtor = requireConstructor(unitClass, String.class, char.class, int.class);

        Method deploy           = requireMethod(cmdClass, "deploy", boolean.class, int.class, int.class, unitClass);
        Method getTotalStrength = requireMethod(cmdClass, "getTotalStrength", int.class);
        Method getTotalUnits    = requireMethod(cmdClass, "getTotalUnits", int.class);
        Method getStatus        = requireMethod(cmdClass, "getStatus", String.class);

        Object zara = newInstance(cmdCtor, "Zara", 3, 4);

        // Empty grid
        assertEquals(0, (int) invoke(getTotalStrength, zara),
                "getTotalStrength should return 0 on an empty grid.");
        assertEquals(0, (int) invoke(getTotalUnits, zara),
                "getTotalUnits should return 0 on an empty grid.");

        Object archer = newInstance(unitCtor, "Archer", 'A', 3);
        Object knight = newInstance(unitCtor, "Knight", 'K', 4);
        Object scout  = newInstance(unitCtor, "Scout",  'S', 4);

        invoke(deploy, zara, 0, 0, archer);
        invoke(deploy, zara, 1, 2, knight);
        invoke(deploy, zara, 2, 3, scout);

        assertEquals(3, (int) invoke(getTotalUnits, zara),
                "getTotalUnits should return 3 after three successful deploys.");
        assertEquals(11, (int) invoke(getTotalStrength, zara),
                "getTotalStrength should return 11 (3 + 4 + 4).");

        assertEquals("Commander: Zara | Units: 3 | Strength: 11 | Score: 11",
                invoke(getStatus, zara),
                "getStatus should return the formatted status string.");
    }

    // ============================================================
    // Display the completed grid after all tests run
    // ============================================================

    @AfterAll
    public void displayFinalBattleGrid() {
        System.out.println("\n");
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║        BATTLE GRID COMPLETE!         ║");
        System.out.println("╠══════════════════════════════════════╣");

        try {
            Class<?> gridClass = Class.forName("BattleGrid");
            Class<?> unitClass = Class.forName("Unit");

            Constructor<?> gridCtor = gridClass.getDeclaredConstructor(int.class, int.class);
            Constructor<?> unitCtor = unitClass.getDeclaredConstructor(String.class, char.class, int.class);
            Method placeUnit        = gridClass.getDeclaredMethod("placeUnit", int.class, int.class, unitClass);
            Method toString         = gridClass.getDeclaredMethod("toString");
            Method strengthInRow    = gridClass.getDeclaredMethod("strengthInRow", int.class);
            Method strengthInCol    = gridClass.getDeclaredMethod("strengthInCol", int.class);
            Method getStrongestRow  = gridClass.getDeclaredMethod("getStrongestRow");

            // Build a 5x6 showcase grid
            Object grid = gridCtor.newInstance(5, 6);

            Object[][] units = {
                    { unitCtor.newInstance("Archer",  'A', 3), 0, 1 },
                    { unitCtor.newInstance("Brute",   'B', 5), 0, 4 },
                    { unitCtor.newInstance("Scout",   'S', 1), 1, 0 },
                    { unitCtor.newInstance("Knight",  'K', 4), 1, 3 },
                    { unitCtor.newInstance("Knight",  'K', 4), 1, 5 },
                    { unitCtor.newInstance("Dragon",  'D', 9), 2, 2 },
                    { unitCtor.newInstance("Archer",  'A', 3), 3, 1 },
                    { unitCtor.newInstance("Scout",   'S', 1), 3, 4 },
                    { unitCtor.newInstance("Brute",   'B', 5), 4, 0 },
                    { unitCtor.newInstance("Knight",  'K', 4), 4, 3 },
                    { unitCtor.newInstance("Dragon",  'D', 9), 4, 5 },
            };

            for (Object[] entry : units) {
                placeUnit.invoke(grid, (int) entry[1], (int) entry[2], entry[0]);
            }

            String gridStr = (String) toString.invoke(grid);
            String[] gridLines = gridStr.split("\n");

            // Print grid with row strength labels
            System.out.println("║  FINAL BATTLE DEPLOYMENT           ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  Col:  0  1  2  3  4  5  Strength  ║");
            System.out.println("║       ─────────────────────────────  ║");

            for (int r = 0; r < gridLines.length; r++) {
                int str = (int) strengthInRow.invoke(grid, r);
                System.out.printf("║  [%d]  %s    %2d       ║%n", r, gridLines[r], str);
            }

            System.out.println("║       ─────────────────────────────  ║");

            // Column strengths
            StringBuilder colRow = new StringBuilder("║  Str: ");
            for (int c = 0; c < 6; c++) {
                int cs = (int) strengthInCol.invoke(grid, c);
                colRow.append(String.format("%-3d", cs));
            }
            while (colRow.length() < 40) colRow.append(" ");
            colRow.append("║");
            System.out.println(colRow.toString());

            int strongest = (int) getStrongestRow.invoke(grid);
            System.out.println("║                                      ║");
            System.out.printf( "║  Strongest Row: Row %d                ║%n", strongest);
            System.out.println("╚══════════════════════════════════════╝");

        } catch (Exception e) {
            System.out.println("║  (Grid display requires all classes  ║");
            System.out.println("║   to be fully implemented.)          ║");
            System.out.println("╚══════════════════════════════════════╝");
        }
    }
}