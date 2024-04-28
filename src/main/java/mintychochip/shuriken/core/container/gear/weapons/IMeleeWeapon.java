package mintychochip.shuriken.core.container.gear.weapons;

import java.util.Collection;
import mintychochip.shuriken.core.container.DamageType;

public interface IMeleeWeapon extends IWeapon {

  double getMaxRange();

  double getMinRange();
  
  void setMaxRange(double range);

  void setMinRange(double range);
}
