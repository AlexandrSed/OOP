package seaBattle.model;

/**
 * класс острова
 */
public class Island extends CellObjectSeesOtherCells{

    public Island(Field field, Cell cell) {
        this.field =field;
        this.position = cell;
        cell.setObjectInCell(this);

        makeCellVisible(cell);
    }
}
