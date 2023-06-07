package seaBattle.model.event;

import java.util.EventListener;

public interface PlayerActionListener extends EventListener {

    void playerMadeMove (PlayerActionEvent event);

    void playerWin();
}
