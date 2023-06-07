package seaBattle.model;

import java.util.ArrayList;

/**
 * Объект, располагающийся в ячейке.
 */
public abstract class CellObject {

    protected Field field;

    protected Cell position;

    public Cell getPosition() {
        return position;
    }

    public Field field() {
        return field;
    }
}
