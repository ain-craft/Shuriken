package mintychochip.shuriken.core.listener;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import mintychochip.genesis.container.Grasper;
import mintychochip.shuriken.core.container.handlers.ActionSource;
import mintychochip.shuriken.core.container.handlers.IHandler;
import mintychochip.shuriken.core.container.handlers.holder.Damage;
import mintychochip.shuriken.core.container.gear.AbstractGear.MeleeWeapon;
import mintychochip.shuriken.core.container.gear.GearType;
import mintychochip.shuriken.core.container.handlers.holder.Status;
import mintychochip.shuriken.core.events.ActionEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.damage.DamageType;
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
  private void onMeleeWeaponAttack(final PlayerInteractEvent event) {
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
    MeleeWeapon grab = weaponGrasper.grab(item.getItemMeta(), GearType.MELEE_WEAPON.getKey(),
        MeleeWeapon.class);
    if (grab == null) {
      return;
    }
    Entity fetch = this.fetch(player, grab.getMinRange(), grab.getMaxRange());
    if (fetch == null) {
      return;
    }

    Iterator<Status> iterator = grab.iterator();
    while(iterator.hasNext()) {
      Status next = iterator.next();
      next.getHandlerList().forEach(handler -> handler.applyIf(grab.getStatusChance(next.getNamespace()) <= new Random().nextDouble(), new ActionSource(
          DamageType.MAGIC,player,fetch)));
    }
//    Collection<DamageType> damageTypes = grab.getDamageTypes();
//    damageTypes.forEach(damageType -> {
//      Damage damage = Damage.getDamageType(damageType);
//      damage.getHandlerList().forEach(handler -> {
//        handler.apply(new ActionSource(org.bukkit.damage.DamageType.MAGIC, player, fetch));
//      });
//    });
  }
  @EventHandler
  private void onAction(final ActionEvent event) {
    event.getAction().action(event);
  }
  private ItemStack getItem(PlayerInventory inventory) {
    return this.getItem(inventory.getItemInMainHand(), inventory.getItemInOffHand());
  }

  private ItemStack getItem(ItemStack main, ItemStack off) {
    return main.getType().isAir() ? off : main;
  }

  private Entity fetch(Player player, double minRange, double maxRange) {
    World world = player.getWorld();
    Location eyeLocation = player.getEyeLocation();
    RayTraceResult rayTraceResult = world.rayTraceEntities(eyeLocation,
        player.getEyeLocation().getDirection(), maxRange, 0.7, entity ->
            !entity.equals(player)
    );
    if (rayTraceResult == null) {
      return null;
    }
    Entity hitEntity = rayTraceResult.getHitEntity();
    if (hitEntity == null) {
      return null;
    }
    Bukkit.broadcastMessage(minRange + "");
    if (hitEntity.getLocation().distance(eyeLocation) <= minRange && minRange > 0) {
      return null;
    }
    return hitEntity;
  }
}
