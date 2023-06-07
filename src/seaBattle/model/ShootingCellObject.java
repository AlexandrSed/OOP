package seaBattle.model;

import seaBattle.model.event.PlayerActionEvent;
import seaBattle.model.event.PlayerActionListener;

import java.util.ArrayList;

/**
 * Объект, располагающийся в ячейке,
 * который может стрелять по ячейкам на поле.
 */
public abstract class ShootingCellObject extends CellObject{
    public void shoot(Cell cell, Field enemyField) {

        if (cell == null) {
            throw new RuntimeException("ERROR: the cell does not exist");
        }

        if (cell.isHit()) {
            throw new RuntimeException("ERROR: the cell has already been shot at");
        }

        cell.makeInaccessibleToShot();

        if (cell.objectInCell() != null && cell.objectInCell() instanceof Ship) {

            boolean allCellsIsHit = true;

            for (Cell shipCell : ((Ship) cell.objectInCell()).getCells()) {

                if (!shipCell.isHit()) {
                    allCellsIsHit = false;
                    break;
                }
            }

            if (allCellsIsHit) {
                ((Ship) cell.objectInCell()).sinkShip();
            }
        }

        if (enemyField.isAllShipsDestroyed()) {
            // Отправить сигнал о передаче хода
            firePlayerWin();
        }
    }

    // -------------------- События --------------------
    private ArrayList<PlayerActionListener> playerActionListeners = new ArrayList<>();

    public void addPlayerActionListener(PlayerActionListener listener) {
        playerActionListeners.add(listener);
    }

    public void removePlayerActionListener(PlayerActionListener listener) {
        playerActionListeners.remove(listener);
    }

    private void firePlayerWin() {
        for(PlayerActionListener listener: playerActionListeners) {

            listener.playerWin();
        }
    }
}
