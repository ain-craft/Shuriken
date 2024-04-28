package mintychochip.shuriken.core.container.handlers;

import java.util.Collection;
import org.bukkit.Keyed;

public interface IHandlerHolder<T extends IHandlerHolder<T>> extends Keyed {
   T addHandler(IHandler<? extends IHandler<?>> handler);
   Collection<IHandler<? extends IHandler<?>>> getHandlers();
}
