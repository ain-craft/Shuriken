package mintychochip.shuriken.core.container.gear;

import mintychochip.shuriken.Shuriken;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public enum GearType implements Keyed {
  MELEE_WEAPON("melee_weapon"),
  BOW("bow");

  private final String namespace;

  GearType(String namespace) {
    this.namespace = namespace;
  }

  public String getNamespace() {
    return namespace;
  }
  @NotNull
  @Override
  public NamespacedKey getKey() {
    return new NamespacedKey(Shuriken.getInstance(), namespace);
  }
}
