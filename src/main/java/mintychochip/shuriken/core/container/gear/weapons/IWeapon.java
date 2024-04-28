package mintychochip.shuriken.core.container.gear.weapons;

import java.util.Collection;
import java.util.Iterator;
import mintychochip.shuriken.core.container.DamageType;
import mintychochip.shuriken.core.container.handlers.IAction;
import mintychochip.shuriken.core.container.handlers.IHandler;
import mintychochip.shuriken.core.container.handlers.holder.Status;

public interface IWeapon {
 Double getStatusChance(String namespace);
 void addStatus(String namespace, Double chance);
 Double getPercent(DamageType damageType);
 void addTypePercent(DamageType damageType, Double percent);
 Collection<DamageType> getDamageTypes(); //for removal
 Iterator<Status> iterator();
}
