package mintychochip.shuriken.core.container;

import org.bukkit.Keyed;

public interface IHandlerHolder<T extends IHandlerHolder<T>> extends Keyed {
   T addHandler(IHandler<? extends IHandler<?>> handler);
}
