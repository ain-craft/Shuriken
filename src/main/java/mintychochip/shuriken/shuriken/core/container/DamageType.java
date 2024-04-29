package mintychochip.shuriken.shuriken.core.container;

import java.util.Arrays;
import mintychochip.shuriken.Shuriken;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public enum DamageType implements Keyed {
  FLAME("flame"),
  CHILLING("cold"),
  POISON("poison"),
  ELECTRIC("electric"),
  PHYSICAL("physical"),
  EXPLOSIVE("explosive"),
  WATER("water"),
  HOLY("holy");
  private final String namespace;

  DamageType(String namespace) {
    this.namespace = namespace;
  }

  public String getNamespace() {
    return namespace;
  }

  public static DamageType find(String namespace) {
    return Arrays.stream(DamageType.values()).filter(type -> type.namespace.equalsIgnoreCase(namespace)).findFirst().orElse(null);
  }

  @Override
  public @NotNull NamespacedKey getKey() {
    return new NamespacedKey(Shuriken.getInstance(),namespace);
  }
}
