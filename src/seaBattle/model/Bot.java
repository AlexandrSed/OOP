package seaBattle.model;

import seaBattle.model.event.PlayerActionEvent;
import seaBattle.model.event.PlayerActionListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * класс бота
 */
public class Bot {

    private Ship woundedEnemyShip;

    public void shoot(Field field) {

        Cell target = selectRandomCellInTheField(field);

        target.makeInaccessibleToShot();

        if (target.objectInCell() != null && target.objectInCell() instanceof Ship) {

            boolean allCellsIsHit = true;

            setWoundedEnemyShip((Ship) target.objectInCell());

            for (Cell shipCell : ((Ship) target.objectInCell()).getCells()) {

                if (!shipCell.isHit()) {
                    allCellsIsHit = false;
                    break;
                }
            }

            if (allCellsIsHit) {
                ((Ship) target.objectInCell()).sinkShip();
                setWoundedEnemyShip(null);
            }
        }
        else if (!(target.objectInCell() instanceof Island)){

            if (target.objectInCell() != null && target.objectInCell() instanceof UnderwaterMine) {
                ((UnderwaterMine) target.objectInCell()).explode();
            }
            // Отправить сигнал о передаче хода
            firePlayerMadeMove(field);
        }

        if (field.isAllShipsDestroyed()) {
            // Отправить сигнал о победе
            firePlayerWin();
        }

    }

    private Cell selectRandomCellInTheField(Field field) {

        if (woundedEnemyShip == null) {
            return selectRandomAccessibleCellInTheList(new ArrayList<Cell>(field.getCells().values()));
        }
        else {
            ArrayList<Cell> hitSells = new ArrayList<Cell>();

            for (Cell shipCell : woundedEnemyShip.getCells()) {
                if (shipCell.isHit()) {
                    hitSells.add(shipCell);
                }
            }

            if (hitSells.size() == 1) {
                return selectRandomAccessibleCellInTheList(hitSells.get(0).neighboringCells());
            }
            else {
                return selectRandomAccessibleCellInTheList(selectTwoCellsThatContinueRow(hitSells, field));
            }
        }
    }

    private void setWoundedEnemyShip(Ship woundedEnemyShip) {
        this.woundedEnemyShip = woundedEnemyShip;
    }

    private Cell selectRandomAccessibleCellInTheList(ArrayList<Cell> cells) {

        Cell cell = cells.get((int)(Math.random() * cells.size()));

        if (cell.isHit()) {
            cells.remove(cell);
            if (cells.size() > 0) {
                cell = selectRandomAccessibleCellInTheList(cells);
            }
            else {
                System.out.println("Че за ???");
                return null;
            }
        }
            System.out.println("shoot to " + cell.position().toString() + "Vasya" + " field");
        return cell;
    }

    private ArrayList<Cell> selectTwoCellsThatContinueRow(ArrayList<Cell> row, Field field) {

        ArrayList<Cell> cells = new ArrayList<Cell>();
        Point point1 = new Point(row.get(0).position());
        Point point2 = new Point(row.get(0).position());

        cells = fillInGapsInRowOfCells(row, field);

        if (cells.size() != 0) {
            return cells;
        }

        for( int i = 1; i < row.size(); i++) {
            if (row.get(0).position().x == row.get(i).position().x) {
                if (Math.min(row.get(i).position().y, point1.y) >= 0) {
                    int y = Math.min(row.get(i).position().y, point1.y);
                    if (row.contains(field.getCells().get(new Point(point1.x, y)))) {
                        y--;
                    }
                    point1.setLocation(row.get(0).position().x, y);
                }
                else {
                    point1.setLocation(-1, -1);
                }

                if (Math.max(row.get(i).position().y, point2.y) + 1 < field.getHeight()) {
                    int y = Math.max(row.get(i).position().y, point2.y);
                    if (row.contains(field.getCells().get(new Point(point2.x, y)))) {
                        y++;
                    }
                    point2.setLocation(row.get(0).position().x, y);
                }
                else {
                    point2.setLocation(field.getWidth(), field.getHeight());
                }
            }
            else if (row.get(0).position().y == row.get(i).position().y) {
                if (Math.min(row.get(i).position().x, point1.x) >= 0) {
                    int x = Math.min(row.get(i).position().x, point1.x);
                    if (row.contains(field.getCells().get(new Point(x, point1.y)))) {
                        x--;
                    }
                    point1.setLocation(x, row.get(0).position().y);
                }
                else {
                    point1.setLocation(-1, -1);
                }

                if (Math.max(row.get(i).position().x, point2.x) + 1 < field.getWidth()) {
                    int x = Math.max(row.get(i).position().x, point2.x);
                    if (row.contains(field.getCells().get(new Point(x, point2.y)))) {
                        x++;
                    }
                    point2.setLocation(x, row.get(0).position().y);
                }
                else {
                    point2.setLocation(field.getWidth(), field.getHeight());
                }
            }
            else {
                throw new RuntimeException("the cells do not make up a row");
            }
        }

        if (!(point1.x < 0 || point1.x >= field.getWidth() || point1.y < 0 || point1.y >= field.getHeight())) {
            cells.add(field.getCells().get(point1));
        }

        if (!(point2.x < 0 || point2.x >= field.getWidth() || point2.y < 0 || point2.y >= field.getHeight())) {
            cells.add(field.getCells().get(point2));
        }

        return cells;
    }

    private ArrayList<Cell> fillInGapsInRowOfCells(ArrayList<Cell> row, Field field) {
        ArrayList<Cell> cells = new ArrayList<Cell>();

        if (row.size() == 2) {
            if (row.get(0).position().x == row.get(1).position().x) {
                for (int i = 1; i < Math.abs(row.get(0).position().y - row.get(1).position().y); i++) {

                    cells.add(field.getCells().get(new Point(row.get(0).position().x, row.get(0).position().y + i)));

                }
            }
            else if (row.get(0).position().y == row.get(1).position().y) {
                for (int i = 1; i < Math.abs(row.get(0).position().x - row.get(1).position().x); i++) {

                    cells.add(field.getCells().get(new Point(row.get(0).position().x + i, row.get(0).position().y)));

                }
            }
        }
        else {
            if (row.get(0).position().x == row.get(1).position().x) {
                for(int i = 0; i < 3; i++) {
                    if (!row.contains(field.getCells().get(new Point(row.get(i).position().x, row.get(i).position().y + 1))) &&
                            !row.contains(field.getCells().get(new Point(row.get(i).position().x, row.get(i).position().y - 1)))) {
                        if (i != 1 && row.get(i).position().y > row.get(1).position().y) {
                            cells.add(field.getCells().get(new Point(row.get(i).position().x, row.get(i).position().y - 1)));
                        } else if (i != 1 && row.get(i).position().y < row.get(1).position().y) {
                            cells.add(field.getCells().get(new Point(row.get(i).position().x, row.get(i).position().y + 1)));
                        } else if (i == 1 && row.get(i).position().y > row.get(0).position().y) {
                            cells.add(field.getCells().get(new Point(row.get(i).position().x, row.get(i).position().y - 1)));
                        } else if (i == 1 && row.get(i).position().y < row.get(0).position().y) {
                            cells.add(field.getCells().get(new Point(row.get(i).position().x, row.get(i).position().y + 1)));
                        }
                    }
                }
            }
            else if (row.get(0).position().y == row.get(1).position().y) {
                for(int i = 0; i < 3; i++) {
                    if (!row.contains(field.getCells().get(new Point(row.get(i).position().x + 1, row.get(i).position().y))) &&
                            !row.contains(field.getCells().get(new Point(row.get(i).position().x - 1, row.get(i).position().y)))) {
                        if (i != 1 && row.get(i).position().x > row.get(1).position().x) {
                            cells.add(field.getCells().get(new Point(row.get(i).position().x - 1, row.get(i).position().y)));
                        } else if (i != 1 && row.get(i).position().x < row.get(1).position().x) {
                            cells.add(field.getCells().get(new Point(row.get(i).position().x + 1, row.get(i).position().y)));
                        } else if (i == 1 && row.get(i).position().x > row.get(0).position().x) {
                            cells.add(field.getCells().get(new Point(row.get(i).position().x - 1, row.get(i).position().y)));
                        } else if (i == 1 && row.get(i).position().x < row.get(0).position().x) {
                            cells.add(field.getCells().get(new Point(row.get(i).position().x + 1, row.get(i).position().y)));
                        }
                    }
                }
            }
        }

        return cells;
    }

    // -------------------- События --------------------
    private ArrayList<PlayerActionListener> playerActionListeners = new ArrayList<>();

    public void addPlayerActionListener(PlayerActionListener listener) {
        playerActionListeners.add(listener);
    }

    public void removePlayerActionListener(PlayerActionListener listener) {
        playerActionListeners.remove(listener);
    }

    private void firePlayerMadeMove(Field enemyField) {
        for(PlayerActionListener listener: playerActionListeners) {
            PlayerActionEvent event = new PlayerActionEvent(listener);
            event.setEnemyField(enemyField);
            listener.playerMadeMove(event);
        }
    }

    private void firePlayerWin() {
        for(PlayerActionListener listener: playerActionListeners) {

            listener.playerWin();
        }
    }
}
