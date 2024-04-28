package mintychochip.shuriken.core.container.gear.weapons;

import mintychochip.shuriken.core.container.DamageType;

public interface IWeapon {

 Double getPercent(DamageType damageType);
 void addTypePercent(DamageType damageType, Double percent);
}
