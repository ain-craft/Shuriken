package mintychochip.shuriken.core.listener;

import java.util.Collection;
import mintychochip.genesis.container.Grasper;
import mintychochip.shuriken.core.container.ActionSource;
import mintychochip.shuriken.core.container.Damage;
import mintychochip.shuriken.core.container.DamageType;
import mintychochip.shuriken.core.container.gear.GearType;
import mintychochip.shuriken.core.container.gear.weapons.MeleeWeapon;
import mintychochip.shuriken.core.util.Constants;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.RayTraceResult;

public class DamageListener implements Listener {

  @EventHandler
  private void onPlayerAttack(final PlayerInteractEvent event) {
    Action action = event.getAction();
    if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
      return;
    }
    Player player = event.getPlayer();
    ItemStack item = this.getItem(player.getInventory());
    if (item.getType().isAir()) {
      return;
    }
    final Grasper<ItemMeta, MeleeWeapon> weaponGrasper = new Grasper<>() {
    };
    MeleeWeapon grab = weaponGrasper.grab(item.getItemMeta(), GearType.MELEE_WEAPON.getKey(), MeleeWeapon.class);
    if (grab == null) {
      return;
    }
    Entity fetch = this.fetch(player);
    if (fetch == null) {
      return;
    }
    Collection<DamageType> damageTypes = grab.getDamageTypes();
    damageTypes.forEach(damageType -> {
      Damage damage = Damage.getDamageType(damageType);
      damage.getHandlerList().forEach(handler -> {
        handler.apply(new ActionSource(org.bukkit.damage.DamageType.MAGIC, player, fetch));
      });
    });
  }

  private ItemStack getItem(PlayerInventory inventory) {
    return this.getItem(inventory.getItemInMainHand(), inventory.getItemInOffHand());
  }

  private ItemStack getItem(ItemStack main, ItemStack off) {
    return main.getType().isAir() ? off : main;
  }

  private Entity fetch(Player player) {
    World world = player.getWorld();
    RayTraceResult rayTraceResult = world.rayTraceEntities(player.getEyeLocation(),
        player.getEyeLocation().getDirection(), 10, 0.7, entity ->
            !entity.equals(player)
    );
    if (rayTraceResult == null || rayTraceResult.getHitEntity().equals(player)) {
      return null;
    }
    return rayTraceResult.getHitEntity();
  }
}
