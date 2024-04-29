package mintychochip.shuriken.shuriken.core.container.action;

import mintychochip.shuriken.core.container.DamageType;
import mintychochip.shuriken.core.container.handlers.ActionSource;
import mintychochip.shuriken.core.container.handlers.IAction;
import mintychochip.shuriken.core.events.ShurikenDamageEvent;
import org.bukkit.Bukkit;

public class DamageAction implements IAction {
  private final int damage;
  private final DamageType damageType;
  public DamageAction(int damage, DamageType damageType) {
    this.damage = damage;
    this.damageType = damageType;
  }
  @Override
  public void on(ActionSource source) {
    Bukkit.getPluginManager().callEvent(new ShurikenDamageEvent(damageType,source,damage));
  }
}
