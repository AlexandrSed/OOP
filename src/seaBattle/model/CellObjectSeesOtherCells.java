package seaBattle.model;

/**
 * Объект, располагающийся в ячейке,
 * который может открывать ячейки для видимости на поле.
 */
public abstract class CellObjectSeesOtherCells extends CellObject {
    public void makeCellVisible(Cell cell){
        cell.setVisibility(true);
    }
}
