package mintychochip.shuriken.core.container.action;

import mintychochip.shuriken.core.container.ActionSource;
import mintychochip.shuriken.core.container.IAction;
import mintychochip.shuriken.core.events.ActionEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class DamageAction implements IAction {

  private final int damage;
  public DamageAction(int damage) {
    this.damage = damage;
  }
  @Override
  public void action(ActionEvent event) {
    ActionSource source = event.getSource();
    Entity directEntity = source.getDirectEntity();
    if(directEntity instanceof LivingEntity livingEntity) {
      livingEntity.damage(0);
      livingEntity.setHealth(this.getHealth(livingEntity.getHealth(),damage));
    }
  }

  private double getHealth(double health, double damage) {
    return health <= damage ? 0 : health - damage;
  }
}
