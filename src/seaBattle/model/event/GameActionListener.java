package seaBattle.model.event;

import seaBattle.model.Player;

import java.util.EventListener;

public interface GameActionListener extends EventListener {
    void gameEnd(Player winner);
}
