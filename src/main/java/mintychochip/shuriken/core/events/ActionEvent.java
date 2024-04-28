package mintychochip.shuriken.core.events;

import javax.swing.Action;
import mintychochip.genesis.events.AbstractEvent;
import mintychochip.shuriken.core.container.ActionSource;
import mintychochip.shuriken.core.container.IAction;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;

public class ActionEvent extends AbstractEvent implements Cancellable {

  private final IAction action;

  private final ActionSource source;

  private boolean cancelled = false;
  public ActionEvent(IAction action, ActionSource source) {
    this.action = action;
    this.source = source;
  }

  public ActionSource getSource() {
    return source;
  }

  public IAction getAction() {
    return action;
  }

  @Override
  public boolean isCancelled() {
    return cancelled;
  }

  @Override
  public void setCancelled(boolean b) {
    this.cancelled = b;
  }
}
