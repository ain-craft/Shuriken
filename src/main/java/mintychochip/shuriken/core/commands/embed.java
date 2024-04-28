package mintychochip.shuriken.core.commands;

import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.genesis.container.Grasper;
import mintychochip.shuriken.core.container.DamageType;
import mintychochip.shuriken.core.container.gear.GearType;
import mintychochip.shuriken.core.container.gear.weapons.MeleeWeapon;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class embed extends GenericCommandObject implements SubCommand {

  final Grasper<ItemMeta, MeleeWeapon> weaponGrasper = new Grasper<ItemMeta, MeleeWeapon>() {
  };

  public embed(String executor, String description) {
    super(executor, description);
  }

  @Override
  public boolean execute(String[] strings, Player player) {
    if (strings.length < depth) {
      return false;
    }
    ItemStack item = this.getInventory(player.getInventory());
    if (item.getType().isAir()) {
      return false;
    }
    ItemMeta itemMeta = item.getItemMeta();
    MeleeWeapon grab = weaponGrasper.grab(itemMeta, GearType.MELEE_WEAPON.getKey(), MeleeWeapon.class);
    if (grab == null) {
      grab = new MeleeWeapon(GearType.MELEE_WEAPON,500);
    }
    grab.addTypePercent(DamageType.find(strings[depth - 1]), 0.2);
    weaponGrasper.toss(itemMeta, grab);
    item.setItemMeta(itemMeta);
    return false;
  }

  private ItemStack getInventory(PlayerInventory inventory) {
    ItemStack itemInMainHand = inventory.getItemInMainHand();
    ItemStack itemInOffHand = inventory.getItemInOffHand();
    return itemInMainHand.getType().isAir() ? itemInOffHand : itemInMainHand;
  }
}
