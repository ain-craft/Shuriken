package mintychochip.shuriken.core.container.gear;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public class AbstractGear implements IGear, Keyed {

  protected final GearType type;

  protected final int score;

  public AbstractGear(GearType type, int gearScore) {
    this.type = type;
    this.score = gearScore;
  }
  @Override
  public GearType getType() {
    return type;
  }

  @Override
  public int getScore() {
    return score;
  }

  @NotNull
  @Override
  public NamespacedKey getKey() {
    return type.getKey();
  }
}
