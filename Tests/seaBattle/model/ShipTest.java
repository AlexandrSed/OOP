package seaBattle.model;

import org.junit.jupiter.api.Test;
import seaBattle.model.Cell;
import seaBattle.model.Field;
import seaBattle.model.Ship;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс корабля
 */
class ShipTest {

    @Test
    void testSinkShip() {
        Field field = new Field(11, 11);
        Cell cell = field.getCells().get(new Point(1, 1));
        cell.makeInaccessibleToShot();
        ArrayList<Cell> shipCells = new ArrayList<Cell>();
        shipCells.add(cell);
        Ship ship = new Ship(field, shipCells);

        ship.sinkShip();

        assertTrue(ship.isDestroyed());
    }

    @Test
    void testSinkSunkenShip() {
        Field field = new Field(11, 11);
        Cell cell = field.getCells().get(new Point(1, 1));
        cell.makeInaccessibleToShot();
        ArrayList<Cell> shipCells = new ArrayList<Cell>();
        shipCells.add(cell);
        Ship ship = new Ship(field, shipCells);

        ship.sinkShip();
        ship.sinkShip();

        assertTrue(ship.isDestroyed());
    }

    @Test
    void testSinkShipWhoseCellsAreNotHit() {
        Field field = new Field(11, 11);
        Cell cell = field.getCells().get(new Point(1, 1));
        ArrayList<Cell> shipCells = new ArrayList<Cell>();
        shipCells.add(cell);
        Ship ship = new Ship(field, shipCells);

        try {
            ship.sinkShip();
        }
        catch (RuntimeException e) {
            assertEquals("ERROR: not all cells of the ship were shelled", e.getMessage());
        }
    }

    @Test
    void testGetCellsShip() {
        Field field = new Field(11, 11);
        Cell cell = field.getCells().get(new Point(1, 1));
        Cell cell1 = field.getCells().get(new Point(1, 2));
        ArrayList<Cell> shipCells = new ArrayList<Cell>();
        shipCells.add(cell);
        shipCells.add(cell1);
        Ship ship = new Ship(field, shipCells);

        assertEquals(shipCells, ship.getCells());
    }

    @Test
    void testShipWithoutCells() {
        Field field = new Field(11, 11);
        boolean isException = false;
        ArrayList<Cell> shipCells = new ArrayList<Cell>();

        try {
            new Ship(field, shipCells);
        }
        catch (RuntimeException e) {
            assertEquals("ERROR: there are no cells in the passed array", e.getMessage());
            isException = true;
        }

        assertTrue(isException);
    }

    @Test
    void testShipContainsMoreThanFourCells() {
        Field field = new Field(11, 11);
        boolean isException = false;

        ArrayList<Cell> shipCells = new ArrayList<Cell>();
        shipCells.add(field.getCells().get(new Point(1, 1)));
        shipCells.add(field.getCells().get(new Point(1, 2)));
        shipCells.add(field.getCells().get(new Point(1, 3)));
        shipCells.add(field.getCells().get(new Point(1, 4)));
        shipCells.add(field.getCells().get(new Point(1, 5)));

        try {
            new Ship(field, shipCells);
        }
        catch (RuntimeException e) {
            assertEquals("ERROR: the ship contains more than four cells", e.getMessage());
            isException = true;
        }

        assertTrue(isException);
    }

    @Test
    void testShipNullCells() {
        Field field = new Field(11, 11);
        boolean isException = false;

        ArrayList<Cell> shipCells = new ArrayList<Cell>();
        shipCells.add(null);

        try {
            new Ship(field, shipCells);
        }
        catch (RuntimeException e) {
            assertEquals("ERROR: ship cells cannot be null", e.getMessage());
            isException = true;
        }

        assertTrue(isException);
    }

    @Test
    void testOneCellSpecifiedMultipleTimes() {
        Field field = new Field(11, 11);
        boolean isException = false;

        ArrayList<Cell> shipCells = new ArrayList<Cell>();

        shipCells.add(field.getCells().get(new Point(1, 1)));
        shipCells.add(field.getCells().get(new Point(3, 1)));
        shipCells.add(field.getCells().get(new Point(1, 1)));

        try {
            new Ship(field, shipCells);
        }
        catch (RuntimeException e) {
            assertEquals("ERROR: one cell is specified multiple times", e.getMessage());
            isException = true;
        }

        assertTrue(isException);
    }

    @Test
    void testOneCellShipFarFromOthers() {
        Field field = new Field(11, 11);
        boolean isException = false;

        ArrayList<Cell> shipCells = new ArrayList<Cell>();

        shipCells.add(field.getCells().get(new Point(1, 1)));
        shipCells.add(field.getCells().get(new Point(3, 1)));


        try {
            new Ship(field, shipCells);
        }
        catch (RuntimeException e) {
            assertEquals("ERROR: the cells of the ship are far from each other", e.getMessage());
            isException = true;
        }

        assertTrue(isException);
    }

    @Test
    void testSeveralCellShipFarFromOthers() {
        Field field = new Field(11, 11);
        boolean isException = false;

        ArrayList<Cell> shipCells = new ArrayList<Cell>();

        shipCells.add(field.getCells().get(new Point(1, 1)));
        shipCells.add(field.getCells().get(new Point(2, 1)));
        shipCells.add(field.getCells().get(new Point(4, 1)));
        shipCells.add(field.getCells().get(new Point(5, 1)));

        try {
            new Ship(field, shipCells);
        }
        catch (RuntimeException e) {
            assertEquals("ERROR: the cells of the ship are far from each other", e.getMessage());
            isException = true;
        }

        assertTrue(isException);
    }

    @Test
    void testCellsOfShipDoNotLieOnSameLine() {
        Field field = new Field(11, 11);
        boolean isException = false;

        ArrayList<Cell> shipCells = new ArrayList<Cell>();
        shipCells.add(field.getCells().get(new Point(1, 1)));
        shipCells.add(field.getCells().get(new Point(2, 1)));
        shipCells.add(field.getCells().get(new Point(2, 2)));

        try {
            new Ship(field, shipCells);
        }
        catch (RuntimeException e) {
            assertEquals("ERROR: cells of the ship do not lie on the same line", e.getMessage());
            isException = true;
        }

        assertTrue(isException);
    }

    @Test
    void testFieldShip() {
        Field field = new Field(11, 11);
        Cell cell = field.getCells().get(new Point(1, 1));
        Cell cell1 = field.getCells().get(new Point(1, 2));
        ArrayList<Cell> shipCells = new ArrayList<Cell>();
        shipCells.add(cell);
        shipCells.add(cell1);
        Ship ship = new Ship(field, shipCells);

        assertEquals(field, ship.field());
    }

    @Test
    void testNullFieldShip() {
        Cell cell = new Cell(new Point(1, 1));
        Cell cell1 = new Cell(new Point(1, 2));
        boolean isException = false;

        ArrayList<Cell> shipCells = new ArrayList<Cell>();
        shipCells.add(cell);
        shipCells.add(cell1);

        try {
            new Ship(null, shipCells);
        }
        catch (RuntimeException e) {
            assertEquals("ERROR: ship field cannot be null", e.getMessage());
            isException = true;
        }

        assertTrue(isException);
    }
}