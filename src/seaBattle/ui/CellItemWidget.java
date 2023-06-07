package seaBattle.ui;

import javax.swing.*;

/**
 * Виджет, располагающийся в виджете ячейки.
 */
public abstract class CellItemWidget extends JPanel {

    protected boolean visibility;

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
        repaint();
    }

}
