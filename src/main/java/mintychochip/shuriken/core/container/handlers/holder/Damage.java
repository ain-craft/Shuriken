package mintychochip.shuriken.core.container.handlers.holder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import mintychochip.shuriken.core.container.DamageType;
import mintychochip.shuriken.core.container.handlers.IHandler;
import mintychochip.shuriken.core.container.handlers.IHandlerHolder;
import mintychochip.shuriken.core.registry.Reg;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public class Damage implements Keyed, IHandlerHolder<Damage> {

  private final DamageType damageType;

  private final String description;

  private final List<IHandler<? extends IHandler<?>>> handlerList = new ArrayList<>();

  public static Damage getDamageType(String namespace) {
    DamageType type = DamageType.find(namespace);
    return type != null ? getDamageType(type) : null;
  }
  public static Damage getDamageType(DamageType damageType) {
    return Reg.DAMAGE_TYPES.get(damageType.getKey());
  }

  public Damage(DamageType damageType, String description) {
    this.damageType = damageType;
    this.description = description;
  }

  public List<IHandler<? extends IHandler<?>>> getHandlerList() {
    return handlerList;
  }

  public DamageType getDamageType() {
    return damageType;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public @NotNull NamespacedKey getKey() {
    return damageType.getKey();
  }

  @Override
  public Damage addHandler(IHandler<? extends IHandler<?>> handler) {
    handlerList.add(handler);
    return this;
  }

  @Override
  public Collection<IHandler<? extends IHandler<?>>> getHandlers() {
    return handlerList;
  }
}
