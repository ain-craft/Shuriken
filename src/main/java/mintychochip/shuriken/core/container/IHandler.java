package mintychochip.shuriken.core.container;
public interface IHandler<T extends IHandler<T>> {
  IHandler<T> addAction(IAction action);
  void apply(ActionSource source);
}
