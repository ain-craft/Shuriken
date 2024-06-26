package mintychochip.shuriken.core.commands;

import java.util.Set;
import mintychochip.genesis.commands.abstraction.GenericCommand;
import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.genesis.container.Grasper;
import mintychochip.shuriken.core.container.DamageType;
import mintychochip.shuriken.core.container.gear.AbstractGear.Factory;
import mintychochip.shuriken.core.container.gear.AbstractGear.MeleeWeapon;
import mintychochip.shuriken.core.container.gear.GearType;
import org.bukkit.Bukkit;
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
      MeleeWeapon grab = weaponGrasper.grab(itemMeta, GearType.MELEE_WEAPON.getKey(), MeleeWeapon.class);
      if (grab == null) {
        grab = (MeleeWeapon) Factory.INSTANCE.create(GearType.MELEE_WEAPON);
      }
      grab.addTypePercent(DamageType.find(strings[depth - 1]), 0.2);
      grab.setMaxRange(15);
      grab.setMinRange(-1);
      Bukkit.broadcastMessage(grab.getScore() + "");
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
      MeleeWeapon grab = weaponGrasper.grab(itemMeta, GearType.MELEE_WEAPON.getKey(),
          MeleeWeapon.class);
      if (grab == null) {
        grab = (MeleeWeapon) Factory.INSTANCE.create(GearType.MELEE_WEAPON);
      }
      grab.addStatus(strings[depth - 1],0.2);
      grab.addTypePercent(DamageType.find(strings[depth - 1]), 0.2);
      grab.setMaxRange(15);
      grab.setMinRange(-1);
      weaponGrasper.toss(itemMeta, grab);
      item.setItemMeta(itemMeta);
      return false;
    }
  }
}
