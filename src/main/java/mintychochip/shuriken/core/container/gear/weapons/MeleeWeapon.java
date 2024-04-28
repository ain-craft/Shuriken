package mintychochip.shuriken.core.container.gear.weapons;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import mintychochip.shuriken.core.container.DamageType;
import mintychochip.shuriken.core.container.gear.AbstractGear;
import mintychochip.shuriken.core.container.gear.GearType;

public class MeleeWeapon extends AbstractGear implements IMeleeWeapon {

  private double maxRange;
  private double minRange;

  private final Map<DamageType,Double> percentMap = new HashMap<>();
  public MeleeWeapon(GearType type, int gearScore) {
    super(type, gearScore);
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
