package seaBattle.model.event;

import java.util.EventListener;

public interface CellActionListener extends EventListener {
    void cellHasBeenShelled (CellActionEvent event);
}
