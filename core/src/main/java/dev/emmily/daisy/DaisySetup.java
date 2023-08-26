package dev.emmily.daisy;

import dev.emmily.daisy.listeners.InventoryClickListener;
import dev.emmily.daisy.listeners.InventoryOpenListener;
import dev.emmily.daisy.menu.scheduler.UpdatableMenuScheduler;
import dev.emmily.daisy.menu.types.updatable.UpdatableMenuRegistry;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class DaisySetup {
  public static void injectListeners(Plugin plugin) {
    PluginManager pluginManager = plugin.getServer().getPluginManager();

    pluginManager.registerEvents(new InventoryClickListener(), plugin);
    pluginManager.registerEvents(new InventoryOpenListener(), plugin);
    pluginManager.registerEvents(new InventoryClickListener(), plugin);
  }

  public static void injectScheduler(Plugin plugin,
                                     UpdatableMenuRegistry registry) {
    plugin.getServer().getScheduler().scheduleSyncRepeatingTask(
      plugin,
      new UpdatableMenuScheduler(registry),
      0L,
      20
    );
  }
}
