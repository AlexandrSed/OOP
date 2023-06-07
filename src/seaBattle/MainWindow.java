package seaBattle;

import seaBattle.model.event.GameActionListener;
import seaBattle.model.GameModel;
import seaBattle.model.Player;
import seaBattle.ui.FieldWidget;
import seaBattle.ui.StateField;
import seaBattle.ui.WidgetFactory;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.*;

// Главное окно, которое фактически является приложением
public class MainWindow extends JFrame {

    FieldWidget myField;

    FieldWidget enemyField;

    public MainWindow() {

        JPanel content = (JPanel)getContentPane();
        content.setBackground(Color.black);
        content.setLayout(new FlowLayout());

        GameModel game = new GameModel();
        game.addGameActionListener(new GameController());

        myField = new FieldWidget(game.getPlayer1().field(), game.getPlayer2(), new WidgetFactory(), StateField.passiveField);
        enemyField = new FieldWidget(game.getPlayer2().field(), game.getPlayer1(), new WidgetFactory(), StateField.activeField);

        content.add(myField);
        content.add(enemyField);

        pack();
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainWindow();
            }
        });
    }


    private final class GameController implements GameActionListener {

        @Override
        public void gameEnd(Player winner) {

            JOptionPane.showMessageDialog(MainWindow.this, "Выиграл " +
                    winner.getName());

            enemyField.makePassive();
            revalidate();
        }
    }
}
