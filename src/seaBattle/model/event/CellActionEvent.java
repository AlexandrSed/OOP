package seaBattle.model.event;

import seaBattle.model.Cell;

import java.util.EventObject;

public class CellActionEvent extends EventObject {
    private Cell cell;

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public CellActionEvent(Object source) {
        super(source);
    }
}
