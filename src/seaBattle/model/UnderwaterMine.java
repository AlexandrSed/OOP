package seaBattle.model;

import java.awt.*;

/**
 * класс подводной мины
 */
public class UnderwaterMine extends ShootingCellObject{

    public UnderwaterMine(Field field, Cell cell) {
        this.field =field;
        this.position = cell;
        cell.setObjectInCell(this);
    }

     public void explode() {
        for(int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (field().getCells().containsKey(new Point(position.position().x + i, position.position().y + j)) &&
                        !field().getCells().get(new Point(position.position().x + i, position.position().y + j)).isHit()) {

                    Cell cell = field().getCells().get(new Point(position.position().x + i, position.position().y + j));
                    shoot(cell, field);

                    if (cell.objectInCell() != null && cell.objectInCell() instanceof UnderwaterMine) {

                        ((UnderwaterMine) cell.objectInCell()).explode();
                    }
                }
            }
        }
     }
}
