package seaBattle.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * класс игрового поля
 */
public class Field {

    private final int width, height;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    private Map<Point, Cell> cells = new HashMap<Point, Cell>();

    private Player owner;

    private ArrayList<Ship> ships = new ArrayList<>();

    private ArrayList<ShootingCellObject> shootingCellObjects = new ArrayList<>();


    public Field(int width, int height) {
        this.width = width;
        this.height = height;

        formField();
    }

    private void formField() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells.put(new Point(i, j), new Cell(new Point(i, j)));
            }
        }

        for (Map.Entry<Point, Cell> cellEntry: cells.entrySet()) {

            if (getCells().get(new Point(cellEntry.getKey().x + 1, cellEntry.getKey().y)) != null) {

                cellEntry.getValue().addNeighboringCell(getCells().get(new Point(cellEntry.getKey().x + 1, cellEntry.getKey().y)));
            }

            if (getCells().get(new Point(cellEntry.getKey().x - 1, cellEntry.getKey().y)) != null) {

                cellEntry.getValue().addNeighboringCell(getCells().get(new Point(cellEntry.getKey().x - 1, cellEntry.getKey().y)));
            }

            if (getCells().get(new Point(cellEntry.getKey().x, cellEntry.getKey().y + 1)) != null) {

                cellEntry.getValue().addNeighboringCell(getCells().get(new Point(cellEntry.getKey().x, cellEntry.getKey().y + 1)));
            }

            if (getCells().get(new Point(cellEntry.getKey().x, cellEntry.getKey().y - 1)) != null) {

                cellEntry.getValue().addNeighboringCell(getCells().get(new Point(cellEntry.getKey().x, cellEntry.getKey().y - 1)));
            }
        }
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player player) {


        player.setField(this);
        owner = player;
    }

    public Map<Point, Cell> getCells() {
        return cells;
    }

    public void setShipOnField(Ship ship) {
        if(ships.contains(ship)) {
            throw new RuntimeException("ERROR: the ship is already installed on the field");
        }

        if(ships.size() == 10) {
            throw new RuntimeException("ERROR: it is impossible to install the eleventh ship on the field");
        }

        ships.add(ship);
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public ArrayList<ShootingCellObject> getShootingCellObjects() {
        return shootingCellObjects;
    }

    public void addShootingCellObject(ShootingCellObject shootingCellObject) {
        this.shootingCellObjects.add(shootingCellObject);
    }

    public boolean isAllShipsDestroyed() {

        if(ships.size() == 0) {
            throw new RuntimeException("ERROR: no ships on the field");
        }

        boolean isDestroyed = true;
        for(Ship ship : ships) {
            if(!ship.isDestroyed()) {
                isDestroyed = false;
            }
        }
        return isDestroyed;
    }
}
