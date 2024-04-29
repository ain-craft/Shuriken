package mintychochip.shuriken.shuriken.core.registry;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import mintychochip.shuriken.core.container.handlers.holder.Damage;
import mintychochip.shuriken.core.container.handlers.holder.Status;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.jetbrains.annotations.NotNull;

public interface Reg {
  Registry<Damage> DAMAGE_TYPES = new SimpleRegistry<>();
  Registry<Status> STATUS = new SimpleRegistry<>();
  final class SimpleRegistry<T extends Keyed> implements Registry<T> {

    private final Set<T> registrar = new HashSet<>();

    public boolean add(T value) {
      return registrar.add(value);
    }
    @Override
    public @Nullable T get(@NotNull NamespacedKey namespacedKey) {
      return this.stream().filter(entry -> entry.getKey().equals(namespacedKey)).findFirst()
          .orElse(null);
    }
    @Override
    public Stream<T> stream() {
      return registrar.stream();
    }

    @Override
    public Iterator<T> iterator() {
      return registrar.iterator();
    }
  }
}
