package mintychochip.shuriken.core.container.handlers;

import java.util.function.Predicate;
import org.bukkit.Bukkit;

public interface IHandler<T extends IHandler<T>> {
  T addAction(IAction action);
  void apply(ActionSource source);

  default void applyIf(boolean predicate, ActionSource source) {
    if(predicate) {
      this.apply(source);
    }
  }
}
