package seaBattle.model;

import seaBattle.model.event.PlayerActionEvent;
import seaBattle.model.event.PlayerActionListener;

import java.util.ArrayList;

/**
 * класс Игрока
 */
public class Player {

    private String name;

    private Field field;

    private boolean activity;

    public Player(String name) {
        this.name = name;
    }

    void setField(Field field) {
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public void shoot(Cell target, Field enemyField) {
        target.makeInaccessibleToShot();

        if (target.objectInCell() != null && target.objectInCell() instanceof Ship) {
            boolean allCellsIsHit = true;

            for (Cell shipCell : ((Ship) target.objectInCell()).getCells()) {

                if(!shipCell.isHit()) {
                    allCellsIsHit = false;
                }
            }

            if (allCellsIsHit) {
                ((Ship) target.objectInCell()).sinkShip();
            }
        }
        else {

            if (target.objectInCell() != null && target.objectInCell() instanceof UnderwaterMine) {
                ((UnderwaterMine) target.objectInCell()).explode();
            }
            // Отправить сигнал о передаче хода
            firePlayerMadeMove(field);
        }

        if (enemyField.isAllShipsDestroyed()) {
            // Отправить сигнал о передаче хода
            firePlayerWin();
        }
    }

    public Field field() {
        return field;
    }

    void setActivity(boolean status) {
        activity = status;
    }

    public boolean activity() {
        return activity;
    }


    // -------------------- События --------------------
    private ArrayList<PlayerActionListener> playerActionListeners = new ArrayList<>();

    public void addPlayerActionListener(PlayerActionListener listener) {
        playerActionListeners.add(listener);
    }

    public void removePlayerActionListener(PlayerActionListener listener) {
        playerActionListeners.remove(listener);
    }

    private void firePlayerMadeMove(Field enemyField) {
        for(PlayerActionListener listener: playerActionListeners) {
            PlayerActionEvent event = new PlayerActionEvent(listener);
            event.setEnemyField(enemyField);
            listener.playerMadeMove(event);
        }
    }

    private void firePlayerWin() {
        for(PlayerActionListener listener: playerActionListeners) {

            listener.playerWin();
        }
    }
}
