package mintychochip.shuriken.core.container.handlers;

public interface IHandler<T extends IHandler<T>> {
  T addAction(IAction action);
  void apply(ActionSource source);
}
