package mintychochip.shuriken.shuriken.core.container.handlers;

import java.util.Collection;
import mintychochip.shuriken.core.container.handlers.IHandler;
import org.bukkit.Keyed;

public interface IHandlerHolder<T extends IHandlerHolder<T>> extends Keyed {
   T addHandler(
       mintychochip.shuriken.core.container.handlers.IHandler<? extends mintychochip.shuriken.core.container.handlers.IHandler<?>> handler);
   Collection<IHandler<? extends IHandler<?>>> getHandlers();
}
