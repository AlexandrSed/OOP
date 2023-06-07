package seaBattle.model;

import seaBattle.model.event.GameActionListener;
import seaBattle.model.event.PlayerActionEvent;
import seaBattle.model.event.PlayerActionListener;

import java.util.ArrayList;

/**
 * класс игры
 */
public class GameModel {

    private Bot bot;

    private Player player1, player2;

    private Player winner;

    private Player activePlayer;

    public GameModel() {
        player1 = new Player("Vasya");
        player2 = new Player("computer");

        bot = new Bot();

        new Field(11, 11).setOwner(player1);
        new Field(11, 11).setOwner(player2);

        new FieldGenerator().placementOfShipsOnField(player1.field());
        for (ShootingCellObject shootingCellObject : player1.field().getShootingCellObjects()) {
            shootingCellObject.addPlayerActionListener(new PlayerObserver());
        }

        new FieldGenerator().placementOfShipsOnField(player2.field());
        for (ShootingCellObject shootingCellObject : player2.field().getShootingCellObjects()) {
            shootingCellObject.addPlayerActionListener(new PlayerObserver());
        }

        gameStart();
    }

    public void gameStart() {

        player1.addPlayerActionListener(new PlayerObserver());
        player2.addPlayerActionListener(new PlayerObserver());
        bot.addPlayerActionListener(new PlayerObserver());

        passMoveToNextPlayer();

    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        fireGameEnd(winner);
        this.winner = winner;
    }

    public Player activePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
        activePlayer.setActivity(true);
    }

    private Player passMoveToNextPlayer() {
        if (activePlayer == null || activePlayer == player2) {
            setActivePlayer(player1);
            player2.setActivity(false);
        }
        else {
            setActivePlayer(player2);
            player1.setActivity(false);
        }

        return activePlayer;
    }

    /** Events */

    private class PlayerObserver implements PlayerActionListener {

        @Override
        public void playerMadeMove(PlayerActionEvent event) {
            passMoveToNextPlayer();
            while (activePlayer == player2 && winner == null) {
                bot.shoot(player1.field());
            }
        }

        @Override
        public void playerWin() {
            setWinner(activePlayer);
        }
    }

    private ArrayList<GameActionListener> gameActionListeners = new ArrayList<>();

    public void addGameActionListener(GameActionListener listener) {
        gameActionListeners.add(listener);
    }

    public void removeGameActionListener(GameActionListener listener) {
        gameActionListeners.remove(listener);
    }

    private void fireGameEnd(Player winner) {
        for(GameActionListener listener: gameActionListeners) {
            listener.gameEnd(winner);
        }
    }

}
