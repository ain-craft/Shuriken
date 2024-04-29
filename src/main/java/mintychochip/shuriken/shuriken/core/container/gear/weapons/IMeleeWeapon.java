package mintychochip.shuriken.shuriken.core.container.gear.weapons;

import mintychochip.shuriken.core.container.gear.weapons.IWeapon;

public interface IMeleeWeapon extends IWeapon {

  double getMaxRange();

  double getMinRange();
  
  void setMaxRange(double range);

  void setMinRange(double range);
}
