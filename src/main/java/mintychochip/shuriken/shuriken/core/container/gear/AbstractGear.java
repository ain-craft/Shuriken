package mintychochip.shuriken.shuriken.core.container.gear;

import mintychochip.genesis.util.Rarity;
import mintychochip.shuriken.core.container.gear.GearType;
import mintychochip.shuriken.core.container.gear.IGear;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public class AbstractGear implements IGear, Keyed {
  protected final GearType type;
  protected final int score;
  protected final Rarity rarity;
  protected AbstractGear(GearType type, int gearScore, Rarity rarity) {
    this.type = type;
    this.score = gearScore;
    this.rarity = rarity;
  }
  @Override
  public GearType getType() {
    return type;
  }

  @Override
  public int getScore() {
    return score;
  }

  @Override
  public Rarity getRarity() {
    return rarity;
  }

  @NotNull
  @Override
  public NamespacedKey getKey() {
    return type.getKey();
  }

}
