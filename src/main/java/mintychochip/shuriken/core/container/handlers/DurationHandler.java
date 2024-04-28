package mintychochip.shuriken.core.container.handlers;

import com.google.common.base.Function;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.swing.Action;
import mintychochip.shuriken.Shuriken;
import mintychochip.shuriken.core.events.ActionEvent;
import mintychochip.shuriken.core.util.Constants.Timer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitTask;

public final class DurationHandler implements IHandler<DurationHandler> {

  private final double duration;
  private final long interval;
  private final long delay;
  private final Set<IAction> actions;

  public DurationHandler(double duration, long interval, long delay, Set<IAction> actions) {
    this.duration = duration;
    this.interval = interval;
    this.delay = delay;
    this.actions = actions;
  }

  public DurationHandler(double duration, long interval, long delay) {
    this(duration, interval, delay, new HashSet<>());
  }

  @Override
  public DurationHandler addAction(IAction action) {
    actions.add(action);
    return this;
  }

  @Override
  public void apply(ActionSource source) {
    actions.forEach(action -> {
      new DeathTimer(Bukkit.getScheduler()
          .runTaskTimer(Shuriken.getInstance(), new ActionEventCaller(action, source), delay,
              interval), duration, source.getDirectEntity(), Entity::isDead).start();
    });
  }

  final class DeathTimer extends Timer {

    private final Predicate<Entity> filter;

    private final Entity entity;
    private final Object lock = new Object(); // Object for synchronization

    public DeathTimer(BukkitTask task, double duration, Entity entity, Predicate<Entity> filter) {
      super(task, duration);
      this.entity = entity;
      this.filter = filter;
    }

    @Override
    public void start() {
      ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

      // Schedule task to check the filter condition periodically
      ScheduledFuture<?> filterFuture = executor.scheduleAtFixedRate(() -> {
        synchronized (lock) {
          if (filter.test(entity)) {
            task.cancel();
            executor.shutdown();
          }
        }
      }, 0, 500, TimeUnit.MILLISECONDS);

      // Schedule task to cancel the main task after the specified duration
      ScheduledFuture<?> cancelFuture = executor.schedule(() -> {
        synchronized (lock) {
          if (task != null && !task.isCancelled()) {
            task.cancel();
            filterFuture.cancel(true);
            executor.shutdown();
          }
        }
      }, (long) (1000 * duration), TimeUnit.MILLISECONDS);
    }
  }

  final static class ActionEventCaller implements Runnable {

    private final IAction action;
    private final ActionSource source;

    public ActionEventCaller(IAction action, ActionSource source) {
      this.action = action;
      this.source = source;
    }

    @Override
    public void run() {
      Entity directEntity = source.getDirectEntity();
      if (directEntity instanceof LivingEntity livingEntity) {
        Bukkit.getPluginManager().callEvent(new ActionEvent(action, source));
      }
    }
  }


}
