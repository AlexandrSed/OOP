package seaBattle.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * класс расстановки кораблей
 */
public class FieldGenerator {

    public void placementOfShipsOnField(Field field) {
        placementOfSingleDeckShipsOnField(field);

        placementOfTwoDeckShipsOnField(field);

        placementOfThreeDeckShipsOnField(field);

        placementOfFourDeckShipsOnField(field);

        placementOfIslandOnField(field);

        placementOfUnderwaterMineOnField(field);

    }

    private Ship ship(Field field, ArrayList<Cell> cells) {
        return new Ship(field, cells);
    }

    private Island island(Field field, Cell cell) {
        return new Island(field, cell);
    }

    private UnderwaterMine underwaterMine(Field field, Cell cell) {
        return new UnderwaterMine(field, cell);
    }

    private void placementOfSingleDeckShipsOnField(Field field) {

        int size  = field.getShips().size();

        while (field.getShips().size() < size + 4) {

            ArrayList<Cell> cells = new ArrayList<Cell>();

            Point point = new Point((int)(Math.random() * field.getWidth()), (int) (Math.random() * field.getHeight()));

            boolean goodPosition = true;

            goodPosition = canInstallObject(point, field);

            if(goodPosition) {
                cells.add(field.getCells().get(point));
                field.setShipOnField(ship(field, cells));
            }
        }
    }

    private void placementOfTwoDeckShipsOnField(Field field) {

        int size  = field.getShips().size();

        while (field.getShips().size() < size + 3) {

            ArrayList<Cell> cells = new ArrayList<Cell>();

            Point point1 = new Point((int)(Math.random() * field.getWidth()), (int) (Math.random() * field.getHeight()));
            Point point2 = null;

            boolean goodPosition = true;

            goodPosition = canInstallObject(point1, field);

            if (goodPosition) {

                for (Cell neighboringCell : field.getCells().get(point1).neighboringCells()) {
                    if (point2 == null && canInstallObject(neighboringCell.position(), field)) {
                        point2 = neighboringCell.position();
                    }
                }
            }

            if(goodPosition && point2 != null) {
                cells.add(field.getCells().get(point1));
                cells.add(field.getCells().get(point2));
                field.setShipOnField(ship(field, cells));
            }
        }
    }

    private void placementOfThreeDeckShipsOnField(Field field) {

        int size  = field.getShips().size();

        while (field.getShips().size() < size + 2) {

            ArrayList<Cell> cells = new ArrayList<Cell>();

            Point point1 = new Point((int)(Math.random() * field.getWidth()), (int) (Math.random() * field.getHeight()));
            Point point2 = null;
            Point point3 = null;

            boolean goodPosition = true;

            goodPosition = canInstallObject(point1, field);

            if (goodPosition) {

                for (Cell neighboringCell : field.getCells().get(point1).neighboringCells()) {
                    if (point2 == null && canInstallObject(neighboringCell.position(), field)) {
                        point2 = neighboringCell.position();
                    }
                }
            }

            if(goodPosition && point2 != null) {

                cells.add(field.getCells().get(point1));
                cells.add(field.getCells().get(point2));

                for (Cell cellContinuedRow : selectTwoCellsThatContinueRow(cells, field)) {
                    if (point3 == null && cellContinuedRow != null && canInstallObject(cellContinuedRow.position(), field)) {
                        point3 = cellContinuedRow.position();
                    }
                }
            }

            if(goodPosition && point2 != null && point3 != null) {
                cells.add(field.getCells().get(point3));
                field.setShipOnField(ship(field, cells));
            }
        }
    }

    private void placementOfFourDeckShipsOnField(Field field) {

        int size  = field.getShips().size();

        while (field.getShips().size() < size + 1) {

            ArrayList<Cell> cells = new ArrayList<Cell>();

            Point point1 = new Point((int)(Math.random() * field.getWidth()), (int) (Math.random() * field.getHeight()));
            Point point2 = null;
            Point point3 = null;
            Point point4 = null;

            boolean goodPosition = true;

            goodPosition = canInstallObject(point1, field);

            if (goodPosition) {

                for (Cell neighboringCell : field.getCells().get(point1).neighboringCells()) {
                    if (canInstallObject(neighboringCell.position(), field)) {
                        point2 = neighboringCell.position();
                    }
                }
            }

            if(goodPosition && point2 != null) {

                cells.add(field.getCells().get(point1));
                cells.add(field.getCells().get(point2));

                for (Cell cellContinuedRow : selectTwoCellsThatContinueRow(cells, field)) {
                    if (point3 == null && cellContinuedRow != null && canInstallObject(cellContinuedRow.position(), field)) {
                        point3 = cellContinuedRow.position();
                    }
                }
            }

            if(goodPosition && point2 != null && point3 != null) {

                cells.add(field.getCells().get(point3));

                for (Cell cellContinuedRow : selectTwoCellsThatContinueRow(cells, field)) {
                    if (point4 == null && cellContinuedRow != null && canInstallObject(cellContinuedRow.position(), field)) {
                        point4 = cellContinuedRow.position();
                    }
                }
            }

            if(goodPosition && point2 != null && point3 != null && point4 != null) {

                cells.add(field.getCells().get(point4));
                field.setShipOnField(ship(field, cells));
            }
        }
    }

    private void placementOfIslandOnField(Field field) {
        int i = 0;
        while (i < 2) {

            Cell cell;

            Point point = new Point((int)(Math.random() * field.getWidth()), (int) (Math.random() * field.getHeight()));

            boolean goodPosition = true;

            goodPosition = canInstallObject(point, field);

            if(goodPosition) {
                i++;
                cell = field.getCells().get(point);
                island(field, cell);
            }
        }
    }

    private void placementOfUnderwaterMineOnField(Field field) {
        int i = 0;
        while (i < 2) {

            Cell cell;

            Point point = new Point((int)(Math.random() * field.getWidth()), (int) (Math.random() * field.getHeight()));

            boolean goodPosition = true;

            goodPosition = canInstallObject(point, field);

            if(goodPosition) {
                i++;
                cell = field.getCells().get(point);
                field.addShootingCellObject(underwaterMine(field, cell));
            }
        }
    }

    private ArrayList<Cell> selectTwoCellsThatContinueRow(ArrayList<Cell> row, Field field) {

        ArrayList<Cell> cells = new ArrayList<Cell>();
        Point point1 = new Point(row.get(0).position());
        Point point2 = new Point(row.get(0).position());

        for( int i = 1; i < row.size(); i++) {
            if (row.get(0).position().x == row.get(i).position().x) {
                if (Math.min(row.get(i).position().y, point1.y) - 1 >= 0) {
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
                if (Math.min(row.get(i).position().x, point1.x) - 1 >= 0) {
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

        cells.add(field.getCells().get(point1));
        cells.add(field.getCells().get(point2));

        return cells;
    }

    private boolean canInstallObject(Point point, Field field) {

        if (point.x < 0 || point.x >= field.getWidth() || point.y < 0 || point.y >= field.getHeight()) {
            return false;
        }

        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                Cell cell = field.getCells().get(new Point(i + point.x, j + point.y));

                if (cell != null && cell.objectInCell() != null) {
                    return false;
                }
            }
        }

        return true;
    }

}
