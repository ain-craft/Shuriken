package mintychochip.shuriken.core.container.action;

import mintychochip.shuriken.core.container.IAction;
import mintychochip.shuriken.core.events.ActionEvent;
import org.bukkit.Location;
import org.bukkit.Particle;

public class ParticleAction implements IAction {

  private final Particle particle;

  private final int count;
  public ParticleAction(Particle particle, int count) {
    this.particle = particle;
    this.count = count;
  }

  @Override
  public void action(ActionEvent event) {
    Location damageLocation = event.getSource().getDamageLocation();
    damageLocation.getWorld().spawnParticle(particle,damageLocation,count,0.25,0.25,0.25,0);
  }
}
