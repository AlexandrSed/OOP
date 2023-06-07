package seaBattle.ui;

import seaBattle.model.Island;

import java.awt.*;

/**
 * Виджет острова
 */
public class IslandWidget extends CellItemWidget{

    private Island island;

    public IslandWidget(Island island, boolean islandVisibility) {
        this.island = island;

        this.visibility = islandVisibility;
        setPreferredSize(new Dimension(45, 45));
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (visibility) {
            g.setColor(Color.yellow);
            g.fillRect(0, 0, 50, 50);
        }
    }
}
