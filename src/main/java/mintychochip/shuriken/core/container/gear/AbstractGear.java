package mintychochip.shuriken.core.container.gear;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import mintychochip.genesis.util.Rarity;
import mintychochip.genesis.util.WeightedRandom;
import mintychochip.shuriken.core.container.DamageType;
import mintychochip.shuriken.core.container.gear.weapons.IMeleeWeapon;
import mintychochip.shuriken.core.container.handlers.IHandler;
import mintychochip.shuriken.core.container.handlers.holder.Status;
import mintychochip.shuriken.core.registry.Reg;
import org.bukkit.Bukkit;
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
    private final Map<DamageType, Double> percentMap = new HashMap<>();

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

    private Status findStatus(String namespace) {
      return Reg.STATUS.stream().filter(status -> status.getNamespace().equalsIgnoreCase(namespace))
          .findFirst().orElse(null);
    }

    @Override
    public Double getStatusChance(String namespace) {
      return statusMap.getOrDefault(namespace, null);
    }

    @Override
    public Iterator<Status> iterator() {
      return statusMap.keySet().stream()
          .map(this::findStatus).iterator();
    }

    @Override
    public void addStatus(String namespace, Double chance) {
      if (Reg.STATUS.stream()
          .anyMatch(status -> status.getNamespace().equalsIgnoreCase(namespace))) {
        statusMap.put(namespace, chance);
      }
    }

    @Override
    public Double getPercent(DamageType damageType) {
      return percentMap.get(damageType);
    }

    @Override
    public void addTypePercent(DamageType damageType, Double percent) {
      if (percentMap.containsKey(damageType)) {
        percent += percentMap.get(damageType);
      }
      percentMap.put(damageType, percent);
    }
  }
}
