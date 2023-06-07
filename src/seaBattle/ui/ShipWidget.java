package seaBattle.ui;

import seaBattle.model.Ship;

import javax.swing.*;
import java.awt.*;

/**
 * Виджет корабля
 */
public class ShipWidget extends CellItemWidget {

    private Ship ship;

    public enum State {
        isNotHit,
        shipInCellIsWounded,
        IsDestroyed
    }

    private State shipState = State.isNotHit;
    public ShipWidget(Ship ship, boolean shipVisibility) {
        this.ship = ship;
        this.visibility = shipVisibility;

        setPreferredSize(new Dimension(45, 45));
    }

    void wound() {
        shipState = State.shipInCellIsWounded;
        repaint();
    }
    void destroyed() {
        shipState = State.IsDestroyed;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (visibility) {
            g.setColor(Color.green);
            g.fillRect(0, 0, 50, 50);
        }

        if(shipState == State.shipInCellIsWounded) {
            g.setColor(Color.red);
            g.drawLine(0, 0, 45, 45);
            g.drawLine(0, 45, 45, 0);
        }

        if(shipState == State.IsDestroyed) {
            g.setColor(Color.red);
            g.fillRect(0, 0, 50, 50);
        }
    }
}
