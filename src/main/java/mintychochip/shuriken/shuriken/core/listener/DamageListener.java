package mintychochip.shuriken.shuriken.core.listener;

import java.util.function.Predicate;
import mintychochip.genesis.container.Grasper;
import mintychochip.shuriken.core.container.action.DamageAction;
import mintychochip.shuriken.core.container.gear.GearType;
import mintychochip.shuriken.core.container.gear.Weapon.MeleeWeapon;
import mintychochip.shuriken.core.container.handlers.ActionSource;
import mintychochip.shuriken.core.container.handlers.InstantHandler;
import org.bukkit.Location;
import org.bukkit.Particle;
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

  private final Predicate<PlayerInteractEvent> rightClick = event -> {
    Action action = event.getAction();
    return action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR;
  };
  private final Predicate<PlayerInteractEvent> blockNonNull = event -> event.getClickedBlock()
      != null;

  @EventHandler
  private void onMeleeWeaponAttack(final PlayerInteractEvent event) {
    if (rightClick.or(blockNonNull).test(event)) {
      return;
    }
    Player player = event.getPlayer();
    ItemStack item = this.getItem(player.getInventory());
    if (item.getType().isAir()) {
      return;
    }
    final Grasper<ItemMeta, MeleeWeapon> weaponGrasper = new Grasper<>() {
    };
    MeleeWeapon grab = weaponGrasper.grab(item.getItemMeta(), GearType.WEAPON.getKey(),
        MeleeWeapon.class);
    if (grab == null) {
      return;
    }
    Entity fetch = this.fetch(player, grab.getMinRange(), grab.getMaxRange());
    if (fetch == null) {
      return;
    }
    new InstantHandler().addAction(
        new DamageAction(5, mintychochip.shuriken.core.container.DamageType.PHYSICAL)).addAction(
        source -> {
          Location location = source.getDirectEntity().getLocation();
          location.getWorld()
              .spawnParticle(Particle.SWEEP_ATTACK, location.add(0, 1, 0), 1, 0, 0, 0, 1);
        }).apply(new ActionSource(DamageType.MAGIC, player, fetch));
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
    if (hitEntity.getLocation().distance(eyeLocation) <= minRange && minRange > 0) {
      return null;
    }
    return hitEntity;
  }
}
