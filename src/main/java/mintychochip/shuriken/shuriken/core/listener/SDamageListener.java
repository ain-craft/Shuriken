package mintychochip.shuriken.shuriken.core.listener;

import mintychochip.shuriken.core.events.ShurikenDamageEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class SDamageListener implements Listener {

  @EventHandler(priority = EventPriority.MONITOR)
  private void onShurikenDamageEvent(final ShurikenDamageEvent event) {
    if (event.isCancelled()) {
      return;
    }
    LivingEntity livingEntity = (LivingEntity) event.getSource().getDirectEntity();
    livingEntity.damage(0);
    livingEntity.setHealth(this.getHealth(livingEntity.getHealth(), event.getDamage()));
  }

  private double getHealth(double health, double damage) {
    return health <= damage ? 0 : health - damage;
  }
}
