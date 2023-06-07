package seaBattle.model;

import seaBattle.model.Cell;
import seaBattle.model.Field;
import seaBattle.model.Ship;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс ячейки
 */
class CellTest {

    @org.junit.jupiter.api.Test
    void testMakeInaccessibleToShot() {
        Point point = new Point(1, 2);
        Cell cell = new Cell(point);

        cell.makeInaccessibleToShot();

        assertTrue(cell.isHit());
    }

    @org.junit.jupiter.api.Test
    void testMakeInaccessibleToShotIsHitCell() {
        Point point = new Point();
        Cell cell = new Cell(point);

        cell.makeInaccessibleToShot();
        cell.makeInaccessibleToShot();

        assertTrue(cell.isHit());
    }

    @org.junit.jupiter.api.Test
    void testIsNotHitCell() {
        Point point = new Point();
        Cell cell = new Cell(point);

        assertFalse(cell.isHit());
    }

    @org.junit.jupiter.api.Test
    void testPosition() {
        Point point = new Point();
        Cell cell = new Cell(point);

        assertEquals(point, cell.position());
    }

    @org.junit.jupiter.api.Test
    void testShipInCell() {
        Point point = new Point();
        Cell cell = new Cell(point);
        Field field = new Field(11, 11);
        ArrayList<Cell> shipCells = new ArrayList<Cell>();
        shipCells.add(cell);
        Ship ship = new Ship(field, shipCells);

        assertEquals(ship, cell.objectInCell());
    }

    @org.junit.jupiter.api.Test
    void testShipNotInCell() {
        Point point = new Point();
        Cell cell = new Cell(point);

        assertNull(cell.objectInCell());
    }

    @org.junit.jupiter.api.Test
    void testTwoShipInCell() {
        Point point = new Point();
        boolean isException = false;
        Cell cell = new Cell(point);
        Field field = new Field(11, 11);
        ArrayList<Cell> shipCells = new ArrayList<Cell>();
        shipCells.add(cell);
        Ship ship1 = new Ship(field, shipCells);

        try {
            Ship ship2 = new Ship(field, shipCells);
        }
        catch (RuntimeException e) {
            assertEquals("ERROR: there is already a object in this cell", e.getMessage());
            isException = true;
        }

        assertTrue(isException);
    }

    @org.junit.jupiter.api.Test
    void testAddNeighboringCell() {

        Cell cell = new Cell(new Point(1, 2));
        Cell neighboringCell = new Cell(new Point(2, 2));
        ArrayList<Cell> neighboringCells = new ArrayList<>();

        neighboringCells.add(neighboringCell);

        cell.addNeighboringCell(neighboringCell);

        assertEquals(neighboringCells, cell.neighboringCells());
    }

    @org.junit.jupiter.api.Test
    void testAddNeighboringNullCell() {
        Point point = new Point();
        Cell cell = new Cell(point);
        boolean isException = false;

        try {
            cell.addNeighboringCell(null);
        }
        catch (RuntimeException e) {
            assertEquals("ERROR: the element being added cannot be null", e.getMessage());
            isException = true;
        }

        assertTrue(isException);
    }

    @org.junit.jupiter.api.Test
    void testAddSeveralNeighboringCell() {

        Cell cell = new Cell(new Point(1, 2));
        Cell neighboringCell1 = new Cell(new Point(2, 2));
        Cell neighboringCell2 = new Cell(new Point(1, 3));
        ArrayList<Cell> neighboringCells = new ArrayList<>();

        neighboringCells.add(neighboringCell1);
        neighboringCells.add(neighboringCell2);

        cell.addNeighboringCell(neighboringCell1);
        cell.addNeighboringCell(neighboringCell2);

        assertEquals(neighboringCells, cell.neighboringCells());
    }

    @org.junit.jupiter.api.Test
    void testNotAddNeighboringCell() {
        Point point = new Point();
        Cell cell = new Cell(point);

        assertTrue(cell.neighboringCells().isEmpty());
    }

    @org.junit.jupiter.api.Test
    void testCellIsNeighborItself() {
        Point point = new Point();
        Cell cell = new Cell(point);
        boolean isException = false;

        try {
            cell.addNeighboringCell(cell);
        }
        catch (RuntimeException e) {
            assertEquals("ERROR: a cell cannot be a neighbor to itself", e.getMessage());
            isException = true;
        }

        assertTrue(isException);
    }
}