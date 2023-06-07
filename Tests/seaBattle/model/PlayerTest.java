package seaBattle.model;

import org.junit.jupiter.api.Test;
import seaBattle.model.Cell;
import seaBattle.model.Field;
import seaBattle.model.Player;
import seaBattle.model.Ship;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс игрока
 */
class PlayerTest {

    @Test
    void testShoot() {
        Player player = new Player("Volodya");
        Field field = new Field(11, 11);
        Cell cellShip = field.getCells().get(new Point(1, 4));
        Cell target = field.getCells().get(new Point(1, 3));

        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(cellShip);
        Ship ship = new Ship(field, cells);
        field.setShipOnField(ship);

        player.shoot(target, field);

        assertTrue(target.isHit());
    }

    @Test
    void testShootToShipAndDestroyed() {
        Player player = new Player("Volodya");
        Field field = new Field(11, 11);
        Cell target = field.getCells().get(new Point(1, 4));
        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(target);
        Ship ship = new Ship(field, cells);
        field.setShipOnField(ship);

        player.shoot(target, field);

        assertTrue(target.isHit());
        assertTrue(ship.isDestroyed());
    }

    @Test
    void testShootToShip() {
        Player player = new Player("Volodya");
        Field field = new Field(11, 11);
        Cell target = field.getCells().get(new Point(1, 4));
        Cell shipCell = field.getCells().get(new Point(1, 3));
        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(target);
        cells.add(shipCell);
        Ship ship = new Ship(field, cells);
        field.setShipOnField(ship);

        player.shoot(target, field);

        assertTrue(target.isHit());
        assertFalse(ship.isDestroyed());
    }
}