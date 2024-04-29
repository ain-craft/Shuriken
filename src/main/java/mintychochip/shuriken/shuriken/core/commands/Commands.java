package mintychochip.shuriken.shuriken.core.commands;

import java.util.Set;
import mintychochip.genesis.commands.abstraction.GenericCommand;
import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.genesis.container.Grasper;
import mintychochip.genesis.util.Rarity;
import mintychochip.shuriken.core.container.gear.GearType;
import mintychochip.shuriken.core.container.gear.Weapon;
import mintychochip.shuriken.core.container.gear.Weapon.MeleeWeapon;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class Commands {
  private static final Grasper<ItemMeta, MeleeWeapon> weaponGrasper = new Grasper<ItemMeta, MeleeWeapon>() {
  };
  private static ItemStack getInventory(PlayerInventory inventory) {
    ItemStack itemInMainHand = inventory.getItemInMainHand();
    ItemStack itemInOffHand = inventory.getItemInOffHand();
    return itemInMainHand.getType().isAir() ? itemInOffHand : itemInMainHand;
  }
  public static class embed extends GenericCommandObject implements SubCommand {
    public embed(String executor, String description) {
      super(executor, description);
    }
    @Override
    public boolean execute(String[] strings, Player player) {
      if (strings.length < depth) {
        return false;
      }
      ItemStack item = getInventory(player.getInventory());
      if (item.getType().isAir()) {
        return false;
      }
      ItemMeta itemMeta = item.getItemMeta();
      MeleeWeapon grab = weaponGrasper.grab(itemMeta, GearType.WEAPON.getKey(), MeleeWeapon.class);
      if (grab == null) {
        grab = (MeleeWeapon) Weapon.Factory.INSTANCE.create(GearType.WEAPON,500, Rarity.UNCOMMON,5);
      }
      grab.setMaxRange(15);
      grab.setMinRange(-1);
      weaponGrasper.toss(itemMeta, grab);
      item.setItemMeta(itemMeta);
      return false;
    }
  }
  public static class status extends GenericCommand implements SubCommand {

    public status(String executor, String description, Set<String> strings) {
      super(executor, description, strings);
    }

    @Override
    public boolean execute(String[] strings, Player player) {
      if (strings.length < depth) {
        return false;
      }
      ItemStack item = getInventory(player.getInventory());
      if (item.getType().isAir()) {
        return false;
      }
      ItemMeta itemMeta = item.getItemMeta();
      MeleeWeapon grab = weaponGrasper.grab(itemMeta, GearType.WEAPON.getKey(),
          MeleeWeapon.class);
      if (grab == null) {
        grab = (MeleeWeapon) Weapon.Factory.INSTANCE.create(GearType.WEAPON,500, Rarity.UNCOMMON,5);      }
      grab.setMaxRange(15);
      grab.setMinRange(-1);
      weaponGrasper.toss(itemMeta, grab);
      item.setItemMeta(itemMeta);
      return false;
    }
  }
}
