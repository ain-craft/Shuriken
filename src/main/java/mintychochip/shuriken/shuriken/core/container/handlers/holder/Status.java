package mintychochip.shuriken.shuriken.core.container.handlers.holder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import mintychochip.shuriken.Shuriken;
import mintychochip.shuriken.core.container.DamageType;
import mintychochip.shuriken.core.container.handlers.IHandler;
import mintychochip.shuriken.core.container.handlers.IHandlerHolder;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public final class Status implements Keyed, IHandlerHolder<Status> {
  private final DamageType damageType;
  private final String namespace;
  private final String description;
  public Status(DamageType damageType, String namespace, String description) {
    this.damageType = damageType;
    this.namespace = namespace;
    this.description= description;
  }
  private final List<IHandler<? extends IHandler<?>>> handlerList = new ArrayList<>();
  @Override
  public Status addHandler(IHandler<? extends IHandler<?>> handler) {
    handlerList.add(handler);
    return this;
  }

  @Override
  public Collection<IHandler<? extends IHandler<?>>> getHandlers() {
    return handlerList;
  }

  public List<IHandler<? extends IHandler<?>>> getHandlerList() {
    return handlerList;
  }

  public DamageType getDamageType() {
    return damageType;
  }

  public String getNamespace() {
    return namespace;
  }

  public String getDescription() {
    return description;
  }

  @NotNull
  @Override
  public NamespacedKey getKey() {
    return new NamespacedKey(Shuriken.getInstance(),namespace);
  }
}
