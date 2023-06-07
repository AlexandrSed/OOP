package seaBattle.ui;

import seaBattle.model.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Фабрика виджетов
 */
public class WidgetFactory {

    private final Map<Cell, CellWidget> cells = new HashMap<>();
    private final Map<Cell, ShipWidget> ships = new HashMap<>();
    private final Map<Island, IslandWidget> islands = new HashMap<>();
    private final Map<UnderwaterMine, MineWidget> mines = new HashMap<>();

    public CellWidget create(Cell cell, Player owner, Player opponent, boolean objectVisibility) {
        if(cells.containsKey(cell)) {
            return cells.get(cell);
        }

        CellWidget item = new CellWidget(cell, owner, opponent);

        CellObject object = cell.objectInCell();
        if(object instanceof Ship) {
            ShipWidget shipWidget = create(cell, objectVisibility);
            item.setObjectWidget(shipWidget);
        }
        else if (object instanceof Island) {
            IslandWidget islandWidget = create((Island) object, object.getPosition().isVisibility());
            item.setObjectWidget(islandWidget);
        }
        else if (object instanceof UnderwaterMine) {
            MineWidget mineWidget = create((UnderwaterMine) object, objectVisibility);
            item.setObjectWidget(mineWidget);
        }

        cells.put(cell, item);
        return item;
    }

    public CellWidget getWidgetCell( Cell cell) {
        return cells.get(cell);
    }

    public ShipWidget create(Cell cell, boolean shipVisibility) {
        if(ships.containsKey(cell)) return ships.get(cell);

        ShipWidget item = new ShipWidget((Ship) cell.objectInCell(), shipVisibility);

        ships.put(cell, item);

        return item;
    }

    public ShipWidget getWidgetShip(Cell cell) {
        return ships.get(cell);
    }

    public IslandWidget create(Island island, boolean islandVisibility) {
        if(islands.containsKey(island))
            return islands.get(island);

        IslandWidget item = new IslandWidget(island, islandVisibility);

        islands.put(island, item);

        return item;
    }

    public IslandWidget getIslandWidget(Island island) {
        return islands.get(island);
    }

    public MineWidget create(UnderwaterMine mine, boolean mineVisibility) {
        if(mines.containsKey(mine))
            return mines.get(mines);

        MineWidget item = new MineWidget(mine, mineVisibility);

        mines.put(mine, item);

        return item;
    }

    public MineWidget getMineWidget(UnderwaterMine mine) {
        return mines.get(mine);
    }

    public void remove( UnderwaterMine mine) {
        mines.remove(mine);
    }

}
