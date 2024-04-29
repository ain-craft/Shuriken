package mintychochip.shuriken.shuriken.core.container.handlers;

import mintychochip.shuriken.core.container.handlers.ActionSource;
import mintychochip.shuriken.core.container.handlers.IAction;

public interface IHandler<T extends IHandler<T>> {
  T addAction(IAction action);
  void apply(mintychochip.shuriken.core.container.handlers.ActionSource source);

  default void applyIf(boolean predicate, ActionSource source) {
    if(predicate) {
      this.apply(source);
    }
  }
}
