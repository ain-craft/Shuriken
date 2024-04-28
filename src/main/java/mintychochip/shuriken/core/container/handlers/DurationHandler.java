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
              interval), 30, source.getDirectEntity(), Entity::isDead).start();
    });
  }

  final class DeathTimer extends Timer {

    private final Predicate<Entity> filter;

    private final Entity entity;

    public DeathTimer(BukkitTask task, double duration, Entity entity, Predicate<Entity> filter) {
      super(task, duration);
      this.entity = entity;
      this.filter = filter;
    }

    @Override
    public void start() {
      ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
      ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(() -> {
        if (filter.test(entity)) {
          scheduledExecutorService.shutdown();
          this.cancel();
        }
      }, 0, 500, TimeUnit.MILLISECONDS);

      ScheduledFuture<?> schedule = scheduledExecutorService.schedule(() -> {
        scheduledFuture.cancel(true);
        this.cancel();
      }, (long) (duration * 1000), TimeUnit.MILLISECONDS);
      try {
        // Wait for the executor to terminate
        scheduledExecutorService.awaitTermination(10, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
        System.out.println("Exception occurred: " + e.getMessage());
        // Cancel the timeout task if it hasn't occurred yet
        scheduledFuture.cancel(true);
        // Cancel the predicate checking task if it hasn't completed yet
        schedule.cancel(true);
      } finally {
        scheduledExecutorService.shutdown();
      }
    }

    private void cancel() {
      task.cancel();
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
