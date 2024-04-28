package mintychochip.shuriken.core.container;

import java.util.ArrayList;
import java.util.List;
import mintychochip.shuriken.core.events.ActionEvent;

public class InstantHandler implements IHandler<InstantHandler> {

  List<IAction> actions = new ArrayList<>();
  @Override
  public InstantHandler addAction(IAction action) {
    actions.add(action);
    return this;
  }

  @Override
  public void apply(ActionSource source) {
    actions.forEach(action -> {
      action.action(new ActionEvent(action,source));
    });
  }
}
