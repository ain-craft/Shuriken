package mintychochip.shuriken.core.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import mintychochip.genesis.container.Grasper;
import mintychochip.shuriken.Shuriken;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

public class Constants {
  public static class Timer {

    protected final BukkitTask task;

    protected final double duration;
    public Timer(BukkitTask task, double duration) {
      this.task = task;
      this.duration = duration;
    }

    public void start() {
      ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
      scheduledExecutorService.schedule(() -> {
        if (task != null && !task.isCancelled()) {
          task.cancel();
        }
      }, (long) (duration * 1000), TimeUnit.MILLISECONDS);
    }
  }
}
