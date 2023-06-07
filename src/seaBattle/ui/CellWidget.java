package seaBattle.ui;

import seaBattle.model.Cell;
import seaBattle.model.Island;
import seaBattle.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Виджет ячейки
 */
public class CellWidget extends JPanel {

    public enum State {
        IsHit,
        IsNotHit
    }

    private final Cell cell;

    private final Player owner;
    private final Player opponent;

    private MouseListener listener;

    private State cellState = State.IsNotHit;

    private static final int CELL_SIZE = 50;

    public CellWidget(Cell cell, Player owner, Player opponent) {
        this.cell = cell;
        this.owner = owner;
        this.opponent = opponent;
        setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
    }

    public void setObjectWidget(CellItemWidget objectWidget) {

        add(objectWidget);
    }

    public void removeItem(CellItemWidget item) {
        remove(item);
        repaint();
    }

    public void makeActive() {
        listener = new ClickController();
        addMouseListener(listener);
    }

    public void makePassive() {
        removeMouseListener(listener);
    }

    void makeHit() {
        cellState = State.IsHit;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.drawRect(0, 0, CELL_SIZE, CELL_SIZE);


        if(cellState == State.IsHit) {
            g.setColor(Color.blue);
            g.fillOval(15, 15, CELL_SIZE-30, CELL_SIZE-30);
        }
    }

    private class ClickController implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

            if (cellState == State.IsNotHit && !(cell.objectInCell() instanceof Island)) {
                opponent.shoot(cell, owner.field());
                System.out.println("shoot to " + cell.position().toString() + owner.getName() + " field");
                repaint();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }
}

