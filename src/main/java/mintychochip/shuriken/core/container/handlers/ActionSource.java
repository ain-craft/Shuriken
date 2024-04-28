package mintychochip.shuriken.core.container.handlers;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftEntity;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ActionSource implements DamageSource {

  private final DamageType damageType;

  private Entity source;

  private Entity target;

  private final Location targetLocation;

  private final Location sourceLocation;

  private boolean indirect = false;

  public ActionSource(DamageType damageType, Entity source, Entity target) {
    this.damageType = damageType;
    this.source = source;
    this.target = target;
    this.sourceLocation = source.getLocation();
    this.targetLocation = target.getLocation();
  }

  public ActionSource(DamageType damageType, Entity source, Location targetLocation) {
    this.damageType = damageType;
    this.source = source;
    this.sourceLocation = source.getLocation();
    this.targetLocation = targetLocation;
    this.indirect = true;
  }

  public ActionSource(DamageType damageType, Location sourceLocation, Location targetLocation) {
    this.damageType = damageType;
    this.sourceLocation = sourceLocation;
    this.targetLocation = targetLocation;
    this.indirect = true;
  }

  @NotNull
  @Override
  public DamageType getDamageType() {
    return damageType;
  }

  @Nullable
  @Override
  public Entity getCausingEntity() {
    return source;
  }

  @Nullable
  @Override
  public Entity getDirectEntity() {
    return target;
  }

  @Nullable
  @Override
  public Location getDamageLocation() {
    return targetLocation;
  }

  @Nullable
  @Override
  public Location getSourceLocation() {
    return sourceLocation;
  }

  @Override
  public boolean isIndirect() {
    return indirect;
  }

  @Override
  public float getFoodExhaustion() {
    return 0;
  }

  @Override
  public boolean scalesWithDifficulty() {
    return true;
  }
}
