package mintychochip.shuriken.shuriken.core.container.gear;

import java.util.HashMap;
import java.util.Map;
import mintychochip.genesis.util.Rarity;
import mintychochip.shuriken.core.container.DamageType;
import mintychochip.shuriken.core.container.gear.AbstractGear;
import mintychochip.shuriken.core.container.gear.GearType;
import mintychochip.shuriken.core.container.gear.weapons.IMeleeWeapon;
import mintychochip.shuriken.core.container.gear.weapons.IWeapon;

public abstract class Weapon extends AbstractGear implements IWeapon {

  public final static class Factory {
    public static final Factory INSTANCE = new Factory();
    private Factory() {}

    public Weapon create(GearType type, int gearScore, Rarity rarity, double damage) {
      return new MeleeWeapon(type,gearScore,rarity,damage);
    }
  }
  protected final double damage;

  protected final Map<String, Double> statusMap = new HashMap<>();

  protected final Map<DamageType,Double> damageTypeDoubleMap = new HashMap<>();
  protected Weapon(GearType type, int gearScore, Rarity rarity, double damage) {
    super(type, gearScore, rarity);
    this.damage = damage;
  }

  @Override
  public double getDamage() {
    return damage;
  }

  @Override
  public Map<String, Double> getStatusHolder() {
    return statusMap;
  }

  @Override
  public Map<DamageType, Double> getDamageTypeHolder() {
    return damageTypeDoubleMap;
  }

  public final static class MeleeWeapon extends Weapon implements IMeleeWeapon {

    private double maxRange;

    private double minRange;

    public MeleeWeapon(GearType type, int gearScore, Rarity rarity, double damage) {
      super(type, gearScore, rarity, damage);
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
      maxRange = range;
    }

    @Override
    public void setMinRange(double range) {
      minRange = range;
    }
  }
}
