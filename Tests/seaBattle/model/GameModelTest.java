package seaBattle.model;

import org.junit.jupiter.api.Test;
import seaBattle.model.GameModel;
import seaBattle.model.Player;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс игры
 */
class GameModelTest {

    @Test
    void gameStart() {
        GameModel gameModel = new GameModel();

        gameModel.gameStart();

        assertNotNull(gameModel.activePlayer());
    }

    @Test
    void setWinner() {
        GameModel gameModel = new GameModel();
        Player player = new Player("Vasiliy");

        gameModel.setWinner(player);

        assertEquals(player, gameModel.getWinner());
    }

    @Test
    void setActivePlayer() {
        GameModel gameModel = new GameModel();
        Player player = new Player("Vasiliy");

        gameModel.setActivePlayer(player);

        assertEquals(player, gameModel.activePlayer());
        assertTrue(player.activity());
    }
}