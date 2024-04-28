package mintychochip.shuriken.core.container;

import java.util.ArrayList;
import java.util.List;
import mintychochip.shuriken.Shuriken;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public class Status implements Keyed, IHandlerHolder<Status> {

  private final String namespace;

  public Status(String namespace) {
    this.namespace = namespace;
  }
  private final List<IHandler<? extends IHandler<?>>> handlerList = new ArrayList<>();
  @Override
  public Status addHandler(IHandler<? extends IHandler<?>> handler) {
    handlerList.add(handler);
    return this;
  }

  @NotNull
  @Override
  public NamespacedKey getKey() {
    return new NamespacedKey(Shuriken.getInstance(),namespace);
  }
}
