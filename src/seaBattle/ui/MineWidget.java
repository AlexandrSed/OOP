package seaBattle.ui;

import seaBattle.model.UnderwaterMine;

import java.awt.*;

/**
 * Виджет подводной мины
 */
public class MineWidget extends CellItemWidget{

    private UnderwaterMine mine;

    public MineWidget (UnderwaterMine mine, boolean mineVisibility) {
        this.mine = mine;
        this.visibility = mineVisibility;

        setPreferredSize(new Dimension(45, 45));
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (visibility) {
            g.setColor(Color.black);
            g.fillOval(9, 9, 25, 25);
            g.drawLine(9, 9, 34, 34);
            g.drawLine(9, 34, 34, 9);
            g.drawLine(4, 21, 39, 21);
            g.drawLine(21, 4, 21, 39);
        }
    }
}
