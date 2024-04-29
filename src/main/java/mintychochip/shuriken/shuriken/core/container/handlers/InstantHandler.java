package mintychochip.shuriken.shuriken.core.container.handlers;

import java.util.ArrayList;
import java.util.List;
import mintychochip.shuriken.core.container.handlers.ActionSource;
import mintychochip.shuriken.core.container.handlers.IAction;
import mintychochip.shuriken.core.container.handlers.IHandler;

public final class InstantHandler implements IHandler<InstantHandler> {
  private final List<IAction> actions = new ArrayList<>();
  @Override
  public InstantHandler addAction(IAction action) {
    actions.add(action);
    return this;
  }

  @Override
  public void apply(ActionSource source) {
    actions.forEach(action -> {
      action.on(source);
    });
  }
}
