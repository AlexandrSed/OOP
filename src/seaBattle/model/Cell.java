package seaBattle.model;

import seaBattle.model.event.CellActionEvent;
import seaBattle.model.event.CellActionListener;

import java.awt.*;
import java.util.ArrayList;

/**
 * класс ячейки
 */
public class Cell {

    private Point position;

    private boolean visibility = false;

    private boolean isHit = false;

    private CellObject object;

    private ArrayList<Cell> neighboringCells = new ArrayList<>();

    public Cell(Point position) {
        this.position = position;
    }

    public void makeInaccessibleToShot() {
        isHit = true;
        visibility = true;
        fireCellHasBeenShelled();
    }

    public boolean isHit() {
        return isHit;
    }

    public Point position() {
        return position;
    }

    public CellObject objectInCell() {
        return object;
    }

    void setObjectInCell(CellObject object) {
        if(this.object != null) {
            throw new RuntimeException("ERROR: there is already a object in this cell");
        }

        this.object = object;
    }

    public ArrayList<Cell> neighboringCells() {
        return neighboringCells;
    }

    public void addNeighboringCell(Cell cell) {

        if(cell == null) {
            throw new RuntimeException("ERROR: the element being added cannot be null");
        }

        if(cell == this) {
            throw new RuntimeException("ERROR: a cell cannot be a neighbor to itself");
        }

        neighboringCells.add(cell);
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public boolean isVisibility() {
        return visibility;
    }

    // -------------------- События --------------------
    private ArrayList<CellActionListener> cellActionListeners = new ArrayList<>();

    public void addCellActionListener(CellActionListener listener) {
        cellActionListeners.add(listener);
    }

    public void removeCellActionListener(CellActionListener listener) {
        cellActionListeners.remove(listener);
    }

    private void fireCellHasBeenShelled() {
        for(CellActionListener listener: cellActionListeners) {
            CellActionEvent event = new CellActionEvent(listener);

            event.setCell(this);

            listener.cellHasBeenShelled(event);
        }
    }
}
