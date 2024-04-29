package mintychochip.shuriken.shuriken.core.events;

import mintychochip.genesis.events.AbstractEvent;
import mintychochip.shuriken.core.container.DamageType;
import mintychochip.shuriken.core.container.SourceEvent;
import mintychochip.shuriken.core.container.handlers.ActionSource;
import org.bukkit.event.Cancellable;

public final class ShurikenDamageEvent extends AbstractEvent implements Cancellable, SourceEvent {

  private final DamageType damageType;
  private final ActionSource source;
  private double damage;
  private boolean cancelled = false;

  public ShurikenDamageEvent(DamageType damageType, ActionSource source, double damage) {
    this.damageType = damageType;
    this.source = source;
    this.damage = damage;
  }

  public void setDamage(double damage) {
    this.damage = damage;
  }

  public DamageType getDamageType() {
    return damageType;
  }

  public double getDamage() {
    return damage;
  }

  @Override
  public boolean isCancelled() {
    return cancelled;
  }

  @Override
  public void setCancelled(boolean b) {
    cancelled = b;
  }

  @Override
  public ActionSource getSource() {
    return source;
  }
}
