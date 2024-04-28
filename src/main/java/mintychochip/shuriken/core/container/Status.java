package mintychochip.shuriken.core.container;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public class Status implements Keyed, IHandlerHolder<Status> {

  @Override
  public Status addHandler(IHandler<? extends IHandler<?>> handler) {
    return null;
  }

  @NotNull
  @Override
  public NamespacedKey getKey() {
    return null;
  }
}
