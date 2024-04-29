package mintychochip.shuriken.shuriken;

import java.util.stream.Collectors;
import mintychochip.genesis.commands.abstraction.GenericMainCommandManager;
import mintychochip.shuriken.core.commands.Commands.embed;
import mintychochip.shuriken.core.commands.Commands.status;
import mintychochip.shuriken.core.container.DamageType;
import mintychochip.shuriken.core.container.action.DamageAction;
import mintychochip.shuriken.core.container.handlers.InstantHandler;
import mintychochip.shuriken.core.container.handlers.holder.Damage;
import mintychochip.shuriken.core.container.handlers.holder.Status;
import mintychochip.shuriken.core.listener.DamageListener;
import mintychochip.shuriken.core.listener.SDamageListener;
import mintychochip.shuriken.core.registry.Reg;
import mintychochip.shuriken.core.registry.Reg.SimpleRegistry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Shuriken extends JavaPlugin {

  private static Shuriken instance;

  @Override
  public void onEnable() {
    instance = this;
    // Plugin startup logic
    if (Reg.DAMAGE_TYPES instanceof SimpleRegistry<Damage> registry) {
      registry.add(new Damage(DamageType.CHILLING, "asdasd").addHandler(
          new InstantHandler().addAction(new DamageAction(5,DamageType.POISON))));
    }
    if (Reg.STATUS instanceof SimpleRegistry<Status> registry) {
    }
    Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
    Bukkit.getPluginManager().registerEvents(new SDamageListener(),this);
    GenericMainCommandManager genericMainCommandManager = new GenericMainCommandManager("embed",
        "asd");
    genericMainCommandManager.addSubCommand(new embed("embed", "asdad"));
    genericMainCommandManager.addSubCommand(new status("status","asd", Reg.STATUS.stream().map(status -> status.getNamespace()).collect(
        Collectors.toSet())));
    getCommand("embed").setExecutor(genericMainCommandManager);
  }

  public static Shuriken getInstance() {
    return instance;
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
