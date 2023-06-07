package seaBattle.model;

import org.junit.jupiter.api.Test;
import seaBattle.model.*;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс поля
 */
class FieldTest {

    @Test
    void testGetOwner() {
        Field field = new Field(11, 11);
        Player player = new Player("1");
        field.setOwner(player);

        assertEquals(player, field.getOwner());
        assertEquals(field, player.field());
    }

    @Test
    void setShipOnField() {
        Field field = new Field(11, 11);
        ArrayList<Cell> cells = new ArrayList<Cell>();
        cells.add(field.getCells().get(new Point(2, 1)));
        cells.add(field.getCells().get(new Point(2, 2)));

        Ship ship = new Ship(field, cells);

        field.setShipOnField(ship);

        assertTrue(field.getShips().contains(ship));
    }

    @Test
    void setTwoTimesOneShipOnField() {
        Field field = new Field(11, 11);
        boolean isException = false;
        ArrayList<Cell> cells = new ArrayList<Cell>();
        cells.add(field.getCells().get(new Point(2, 1)));
        cells.add(field.getCells().get(new Point(2, 2)));

        Ship ship = new Ship(field, cells);

        field.setShipOnField(ship);

        try {
            field.setShipOnField(ship);
        }
        catch (RuntimeException e) {
            assertEquals("ERROR: the ship is already installed on the field", e.getMessage());
            isException = true;
        }

        assertTrue(isException);
    }

    @Test
    void setEleventhShipOnField() {
        Field field = new Field(11, 11);
        FieldGenerator fieldGenerator = new FieldGenerator();
        boolean isException = false;

        fieldGenerator.placementOfShipsOnField(field);

        ArrayList<Cell> cells = new ArrayList<Cell>();

        while(cells.size() < 1) {

            Cell cell = field.getCells().get(new Point((int)(Math.random() * 10), (int)(Math.random() * 10)));
            if(field.getCells().get(cell.position()).objectInCell() == null)
                cells.add(cell);
        }

        Ship ship = new Ship(field, cells);

        try {
            field.setShipOnField(ship);
        }
        catch (RuntimeException e) {
            assertEquals("ERROR: it is impossible to install the eleventh ship on the field", e.getMessage());
            isException = true;
        }

        assertTrue(isException);
    }

    @Test
    void testIsAllShipsDestroyed() {
        Field field = new Field(11, 11);
        ArrayList<Cell> cells = new ArrayList<Cell>();
        cells.add(field.getCells().get(new Point(2, 1)));

        Ship ship = new Ship(field, cells);

        field.setShipOnField(ship);

        field.getCells().get(new Point(2, 1)).makeInaccessibleToShot();

        ship.sinkShip();

        assertTrue(field.isAllShipsDestroyed());
    }

    @Test
    void testIsNotAllShipsDestroyed() {
        Field field = new Field(11, 11);
        ArrayList<Cell> cells = new ArrayList<Cell>();
        cells.add(field.getCells().get(new Point(2, 1)));

        Ship ship = new Ship(field, cells);

        field.setShipOnField(ship);

        assertFalse(field.isAllShipsDestroyed());
    }

    @Test
    void testIsNotShipsOnField() {
        Field field = new Field(11, 11);
        boolean isException = false;

        try {
            field.isAllShipsDestroyed();
        }
        catch (RuntimeException e) {
            assertEquals("ERROR: no ships on the field", e.getMessage());
            isException = true;
        }

        assertTrue(isException);
    }
}