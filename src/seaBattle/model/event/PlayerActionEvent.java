package seaBattle.model.event;

import java.util.EventObject;

import seaBattle.model.Field;

public class PlayerActionEvent extends EventObject {

    private Field enemyField;

    public Field getEnemyField() {
        return enemyField;
    }

    public void setEnemyField(Field enemyField) {
        this.enemyField = enemyField;
    }

    public PlayerActionEvent(Object source) {
        super(source);
    }
}
