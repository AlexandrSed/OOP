package seaBattle.model.event;

import seaBattle.model.Ship;

import java.util.EventObject;

public class ShipActionEvent extends EventObject {

    private Ship ship;

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public ShipActionEvent(Object source) {
        super(source);
    }
}
