package seaBattle.model;

import seaBattle.model.event.ShipActionEvent;
import seaBattle.model.event.ShipActionListener;

import java.awt.*;
import java.util.ArrayList;

/**
 * класс корабля
 */
public class Ship extends CellObject{

    private boolean isDestroyed = false;

    private ArrayList<Cell> cells;

    public Ship(Field field, ArrayList<Cell> cells) {

        if(field == null) {
            throw new RuntimeException("ERROR: ship field cannot be null");
        }

        if(cells == null || cells.isEmpty()) {
            throw new RuntimeException("ERROR: there are no cells in the passed array");
        }

        if(cells.size() > 4) {
            throw new RuntimeException("ERROR: the ship contains more than four cells");
        }

        int numOfExtremeCells = 0;

        for(int i = 0; i < cells.size(); i++) {
            if(cells.get(i) == null) {
                throw new RuntimeException("ERROR: ship cells cannot be null");
            }

            if(i != cells.size()-1 && cells.get(0).position().x != cells.get(i + 1).position().x
                    && cells.get(0).position().y != cells.get(i + 1).position().y) {
                throw new RuntimeException("ERROR: cells of the ship do not lie on the same line");
            }

            int numCellMeetingsInShip = 0;

            for (Cell cell : cells) {
                if(cells.get(i).position().equals(cell.position())) {
                    numCellMeetingsInShip++;
                }
            }

            if (numCellMeetingsInShip > 1) {
                throw new RuntimeException("ERROR: one cell is specified multiple times");
            }

            int numNeighboringCellsContainsInShip = 0;

            for(Cell neighboringCell : cells.get(i).neighboringCells()) {
                if (cells.contains(neighboringCell)) {
                    numNeighboringCellsContainsInShip ++;
                }
            }

            if (numNeighboringCellsContainsInShip == 0 && cells.size() != 1) {
                throw new RuntimeException("ERROR: the cells of the ship are far from each other");
            }
            else if (numNeighboringCellsContainsInShip == 1) {
                numOfExtremeCells ++;
            }
        }

        if (numOfExtremeCells > 2) {
            throw new RuntimeException("ERROR: the cells of the ship are far from each other");
        }

        for(Cell cell : cells) {
            cell.setObjectInCell(this);
        }

        this.field = field;
        this.cells = cells;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void sinkShip() {
        boolean isDestroyedCellsShip = true;

        for(Cell cellShip : cells) {

            if(!cellShip.isHit()) {
                isDestroyedCellsShip = false;
            }
        }

        if(!isDestroyedCellsShip) {
            throw new RuntimeException("ERROR: not all cells of the ship were shelled");
        }

        isDestroyed = true;
        fireShipDestroyed();

        makeCellsAroundShipInaccessible();
    }

    private void makeCellsAroundShipInaccessible() {


        for(Cell cellShip : cells) {
            for(int i = -1; i < 2; i++) {
                for(int j = -1; j < 2; j++) {

                    Cell cell = field.getCells().get(new Point(i + cellShip.position().x, j + cellShip.position().y));

                    if (cell != null && !cell.isHit() && !cells.contains(cell)) {
                        cell.makeInaccessibleToShot();
                    }
                }
            }
        }
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    // -------------------- События --------------------
    private ArrayList<ShipActionListener> shipActionListeners = new ArrayList<>();

    public void addShipActionListener(ShipActionListener listener) {
        shipActionListeners.add(listener);
    }

    public void removeShipActionListener(ShipActionListener listener) {
        shipActionListeners.remove(listener);
    }

    private void fireShipDestroyed() {
        for(ShipActionListener listener: shipActionListeners) {
            ShipActionEvent event = new ShipActionEvent(listener);

            event.setShip(this);

            listener.shipDestroyed(event);
        }
    }

}