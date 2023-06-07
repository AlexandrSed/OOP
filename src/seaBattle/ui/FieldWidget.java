package seaBattle.ui;

import seaBattle.model.*;
import seaBattle.model.event.CellActionEvent;
import seaBattle.model.event.CellActionListener;
import seaBattle.model.event.ShipActionEvent;
import seaBattle.model.event.ShipActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Виджет поля
 */
public class FieldWidget extends JPanel {
    private final Field field;

    private final Player opponent;

    private final WidgetFactory widgetFactory;
    StateField stateField;

    public FieldWidget(Field field, Player opponent, WidgetFactory widgetFactory, StateField stateField) {
        this.stateField = stateField;
        this.field = field;
        this.opponent = opponent;
        this.widgetFactory = widgetFactory;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        fillField();

        for (Ship ship : field.getShips()) {
            ship.addShipActionListener(new ShipController());
        }
    }

    private void fillField() {

        for (int i = 0; i < field.getHeight(); ++i) {
            JPanel row = createRow(i);
            add(row);
        }
    }

    private JPanel createRow(int rowIndex) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

        for(int i = 0; i < field.getWidth(); ++i) {
            Point point = new Point(i, rowIndex);
            Cell cell = field.getCells().get(point);
            cell.addCellActionListener(new CellController());

            CellWidget cellWidget;

            if (stateField == StateField.passiveField) {
                cellWidget = widgetFactory.create(cell, field.getOwner(), opponent, true);
            }
            else {
                cellWidget = widgetFactory.create(cell, field.getOwner(), opponent, false);
                cellWidget.makeActive();
            }

            row.add(cellWidget);
        }

        return row;
    }

    public void makePassive() {
        this.stateField = StateField.passiveField;

        for (Cell cell : field.getCells().values()) {
            widgetFactory.getWidgetCell(cell).makePassive();
            if (cell.objectInCell() != null && cell.objectInCell() instanceof Ship) {
                widgetFactory.getWidgetShip(cell).setVisibility(true);
            }
            else if (cell.objectInCell() != null && cell.objectInCell() instanceof UnderwaterMine &&
                    widgetFactory.getMineWidget((UnderwaterMine) cell.objectInCell()) != null) {
                widgetFactory.getMineWidget((UnderwaterMine) cell.objectInCell()).setVisibility(true);
            }
        }
        repaint();
    }

    private class CellController implements CellActionListener {


        @Override
        public void cellHasBeenShelled(CellActionEvent event) {

            ActionListener actionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (event.getCell().objectInCell() == null) {
                        widgetFactory.getWidgetCell(event.getCell()).makeHit();
                    }
                    else if (event.getCell().objectInCell() instanceof Ship){
                        widgetFactory.getWidgetShip(event.getCell()).wound();
                    }
                    else if (event.getCell().objectInCell() instanceof UnderwaterMine) {
                        UnderwaterMine mine = (UnderwaterMine) event.getCell().objectInCell();
                        widgetFactory.getWidgetCell(event.getCell()).removeItem(widgetFactory.getMineWidget(mine));
                        widgetFactory.remove(mine);
                        widgetFactory.getWidgetCell(event.getCell()).makeHit();
                    }
                }
            };

            Timer timer;
            if (Objects.equals(opponent.getName(), "computer")) {
                timer = new Timer(700, actionListener);
            }
            else {
                timer = new Timer(0, actionListener);
            }
            timer. setRepeats (false);
            timer.start();
        }
    }

    private class ShipController implements ShipActionListener {


        @Override
        public void shipDestroyed(ShipActionEvent event) {
            for (Cell cell : event.getShip().getCells()) {

                ActionListener actionListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        widgetFactory.getWidgetShip(cell).destroyed();
                    }
                };

                Timer timer;
                if (Objects.equals(opponent.getName(), "computer")) {
                    timer = new Timer(700, actionListener);
                }
                else {
                    timer = new Timer(0, actionListener);
                }
                timer. setRepeats (false);
                timer.start();

            }
            repaint();
        }
    }
}
