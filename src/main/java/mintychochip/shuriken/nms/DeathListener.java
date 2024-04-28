package mintychochip.shuriken.nms;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class DeathListener implements Listener {
  @EventHandler
  private void asd(final EntityDeathEvent event) {
    event.getEntity();
  }
}
