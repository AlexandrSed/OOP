package seaBattle.model;

import org.junit.jupiter.api.Test;
import seaBattle.model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс бота
 */
class BotTest {

    @Test
    void testShoot() {
        Bot bot = new Bot();
        Field field = new Field(11, 11);
        Cell cellShip = field.getCells().get(new Point(1, 4));

        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(cellShip);
        Ship ship = new Ship(field, cells);
        field.setShipOnField(ship);

        bot.shoot(field);

        boolean isShoot = false;

        for (Map.Entry<Point, Cell> cellEntry: field.getCells().entrySet()) {
            if(cellEntry.getValue().isHit()) {
                isShoot = true;
            }
        }

        assertTrue(isShoot);
    }

    @Test
    void testShootFinishingOffShip() {
        Bot bot = new Bot();
        Field field = new Field(11, 11);
        new FieldGenerator().placementOfShipsOnField(field);

        int numHitCellShip = 0;

        while (numHitCellShip < 2) {

            bot.shoot(field);
            numHitCellShip = 0;

            for (Map.Entry<Point, Cell> cellEntry : field.getCells().entrySet()) {
                if (cellEntry.getValue().isHit()) {
                    if (cellEntry.getValue().objectInCell() != null && cellEntry.getValue().objectInCell() instanceof Ship
                            && numHitCellShip == 0) {
                        for(Cell cell : ((Ship) cellEntry.getValue().objectInCell()).getCells()) {
                            if (cell.isHit()) {
                                numHitCellShip ++;
                            }
                        }
                    }
                }
            }
        }

        assertEquals(2, numHitCellShip);
    }
}