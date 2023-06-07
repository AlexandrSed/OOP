package seaBattle.model;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс подводной мины
 */
class UnderwaterMineTest {

    @Test
    void testExplode() {
        Field field = new Field(11, 11);

        new FieldGenerator().placementOfShipsOnField(field);

        ArrayList<ShootingCellObject> mines = field.getShootingCellObjects();

        Point position = mines.get(0).getPosition().position();

        ((UnderwaterMine)mines.get(0)).explode();

        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                if (field.getCells().containsKey(new Point(position.x + i, position.y + j))) {
                    assertTrue(field.getCells().get(new Point(position.x + i, position.y + j)).isHit());
                }
            }
        }

    }

    @Test
    void testExplodeAndHitShip() {
        Field field = new Field(11, 11);

        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(field.getCells().get(new Point(5, 5)));

        field.setShipOnField(new Ship(field, cells));
        field.addShootingCellObject(new UnderwaterMine(field, field.getCells().get(new Point(4, 4))));

        ArrayList<ShootingCellObject> mines = field.getShootingCellObjects();

        ((UnderwaterMine)mines.get(0)).explode();

        assertTrue(field.getShips().get(0).isDestroyed());

    }

    @Test
    void testExplodeAndHitMine() {
        Field field = new Field(11, 11);

        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(field.getCells().get(new Point(0, 0)));

        field.setShipOnField(new Ship(field, cells));

        field.addShootingCellObject(new UnderwaterMine(field, field.getCells().get(new Point(5, 5))));
        field.addShootingCellObject(new UnderwaterMine(field, field.getCells().get(new Point(4, 4))));


        ArrayList<ShootingCellObject> mines = field.getShootingCellObjects();

        Point position = mines.get(1).getPosition().position();

        ((UnderwaterMine)mines.get(0)).explode();

        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                if (field.getCells().containsKey(new Point(position.x + i, position.y + j))) {
                    assertTrue(field.getCells().get(new Point(position.x + i, position.y + j)).isHit());
                }
            }
        }

    }

    @Test
    void testExplodeMineInCornerOfField() {
        Field field = new Field(11, 11);

        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(field.getCells().get(new Point(5, 5)));

        field.setShipOnField(new Ship(field, cells));

        field.addShootingCellObject(new UnderwaterMine(field, field.getCells().get(new Point(0, 0))));

        boolean isException = false;

        try {
            ((UnderwaterMine)field.getShootingCellObjects().get(0)).explode();
        }
        catch (RuntimeException e) {
            isException = true;
        }

        assertFalse(isException);
    }
}