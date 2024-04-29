package mintychochip.shuriken.shuriken.core.container.handlers;

import mintychochip.shuriken.core.container.handlers.ActionSource;

public interface IAction {
  void on(ActionSource source);
}
