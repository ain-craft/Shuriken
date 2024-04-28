package mintychochip.shuriken.core.container.gear;

import mintychochip.genesis.util.Rarity;

public interface IGear {

  GearType getType();
  int getScore();
  Rarity getRarity();
}
