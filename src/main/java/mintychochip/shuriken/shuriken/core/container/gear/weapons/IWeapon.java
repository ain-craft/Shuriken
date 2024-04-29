package mintychochip.shuriken.shuriken.core.container.gear.weapons;

import java.util.Map;
import mintychochip.shuriken.core.container.DamageType;

public interface IWeapon {
 double getDamage();
 Map<String,Double> getStatusHolder();
 Map<DamageType,Double> getDamageTypeHolder();

}
