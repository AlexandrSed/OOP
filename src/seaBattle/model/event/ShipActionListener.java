package seaBattle.model.event;

import java.util.EventListener;

public interface ShipActionListener extends EventListener {
    void shipDestroyed(ShipActionEvent event);
}
