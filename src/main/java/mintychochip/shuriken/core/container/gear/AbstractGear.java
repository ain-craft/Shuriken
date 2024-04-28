package mintychochip.shuriken.core.container.gear;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import mintychochip.genesis.util.Rarity;
import mintychochip.genesis.util.WeightedRandom;
import mintychochip.shuriken.core.container.DamageType;
import mintychochip.shuriken.core.container.gear.weapons.IMeleeWeapon;
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

  public final static class Factory {

    public static final Factory INSTANCE = new Factory();

    private WeightedRandom<Rarity> rarity = new WeightedRandom<>();

    public Factory() {
      rarity.addItem(Rarity.COMMON, 0.35)
          .addItem(Rarity.UNCOMMON, 0.25) // 5
          .addItem(Rarity.RARE, 0.20) //65
          .addItem(Rarity.EPIC, 0.125) // 75
          .addItem(Rarity.LEGENDARY, 0.065)
          .addItem(Rarity.ARTIFACT, 0.01);
    }

    public AbstractGear create(GearType gearType) {
      Rarity rarity = this.rarity.chooseOne();
      return switch (gearType) {
        case MELEE_WEAPON -> new MeleeWeapon(gearType, this.getNum(rarity, 200, 700), rarity);
        //case BOW -> new MeleeWeapon(gearType, this.getNum(rarity,200,700), rarity);
        default -> null;
      };
    }

    private int getNum(Rarity rarity, int min, int max) {
      int score = new Random().nextInt(max - min) + min;
      switch (rarity) {
        case ARTIFACT -> {
          score += 200;
        }
        case LEGENDARY -> {
          score += 150;
        }
        case EPIC -> {
          score += 100;
        }
        case RARE -> {
          score += 50;
        }
      }
      return score;
    }
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
  public final static class MeleeWeapon extends AbstractGear implements IMeleeWeapon {
    private double maxRange;
    private double minRange;
    private final Map<String, Double> statusMap = new HashMap<>();
    private final Map<DamageType,Double> percentMap = new HashMap<>();
    private MeleeWeapon(GearType type, int gearScore, Rarity rarity) {
      super(type, gearScore, rarity);
    }

    @Override
    public double getMaxRange() {
      return maxRange;
    }

    @Override
    public double getMinRange() {
      return minRange;
    }

    @Override
    public void setMaxRange(double range) {
      this.maxRange = range;
    }

    @Override
    public void setMinRange(double range) {
      this.minRange = range;
    }

    @Override
    public Collection<DamageType> getDamageTypes() {
      return percentMap.keySet();
    }
    @Override
    public Double getPercent(DamageType damageType) {
      return percentMap.get(damageType);
    }

    @Override
    public void addTypePercent(DamageType damageType, Double percent) {
      if(percentMap.containsKey(damageType)) {
        percent += percentMap.get(damageType);
      }
      percentMap.put(damageType,percent);
    }
  }
}
