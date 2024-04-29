package mintychochip.shuriken.shuriken.core.container.gear;

import mintychochip.genesis.util.Rarity;
import mintychochip.shuriken.core.container.gear.GearType;

public interface IGear {

  GearType getType();
  int getScore();
  Rarity getRarity();
}
