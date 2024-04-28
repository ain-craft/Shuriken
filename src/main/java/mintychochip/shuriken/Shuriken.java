package mintychochip.shuriken;

import mintychochip.genesis.commands.abstraction.GenericMainCommandManager;
import mintychochip.shuriken.core.commands.embed;
import mintychochip.shuriken.core.container.Damage;
import mintychochip.shuriken.core.container.DamageType;
import mintychochip.shuriken.core.container.InstantHandler;
import mintychochip.shuriken.core.container.action.DamageAction;
import mintychochip.shuriken.core.container.action.ParticleAction;
import mintychochip.shuriken.core.listener.DamageListener;
import mintychochip.shuriken.core.registry.Reg;
import mintychochip.shuriken.core.registry.Reg.SimpleRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;

public final class Shuriken extends JavaPlugin {

    private static Shuriken instance;
    @Override
    public void onEnable() {
        instance=  this;
        // Plugin startup logic
        if(Reg.DAMAGE_TYPES instanceof SimpleRegistry<Damage> registry) {
            registry.add(new Damage(DamageType.CHILLING,"asdasd").addHandler(new InstantHandler().addAction(new DamageAction(5)).addAction(new ParticleAction(
                Particle.SNOWFLAKE,10))));
            registry.add(new Damage(DamageType.FLAME,"").addHandler(new InstantHandler().addAction(new DamageAction(1))));
            registry.add(new Damage(DamageType.HOLY,"").addHandler(new InstantHandler().addAction(new ParticleAction(Particle.TOTEM,3))));
            registry.add(new Damage(DamageType.POISON,"").addHandler(new InstantHandler().addAction(new ParticleAction(Particle.WARPED_SPORE,30))));
            registry.add(new Damage(DamageType.ELECTRIC,"").addHandler(new InstantHandler().addAction(new ParticleAction(Particle.ELECTRIC_SPARK,3))));
        }
        Bukkit.getPluginManager().registerEvents(new DamageListener(),this);
        GenericMainCommandManager genericMainCommandManager = new GenericMainCommandManager("embed",
            "asd");
        genericMainCommandManager.addSubCommand(new embed("embed","asdad"));
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
