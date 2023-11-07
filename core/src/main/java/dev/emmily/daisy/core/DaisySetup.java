package dev.emmily.daisy.core;

import dev.emmily.daisy.api.menu.types.dynamic.registry.DynamicMenuRegistry;
import dev.emmily.daisy.api.menu.types.dynamic.scheduler.DynamicMenuScheduler;
import dev.emmily.daisy.core.listeners.InventoryClickListener;
import dev.emmily.daisy.core.listeners.InventoryCloseListener;
import dev.emmily.daisy.core.listeners.InventoryDragListener;
import dev.emmily.daisy.core.listeners.InventoryOpenListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class DaisySetup {
  public static void injectListeners(Plugin plugin,
                                     DynamicMenuRegistry dynamicMenuRegistry) {
    PluginManager pluginManager = plugin.getServer().getPluginManager();

    pluginManager.registerEvents(new InventoryClickListener(), plugin);
    pluginManager.registerEvents(new InventoryOpenListener(dynamicMenuRegistry), plugin);
    pluginManager.registerEvents(new InventoryCloseListener(dynamicMenuRegistry), plugin);
    pluginManager.registerEvents(new InventoryDragListener(), plugin);
  }

  public static void injectScheduler(Plugin plugin,
                                     DynamicMenuRegistry registry) {
    plugin.getServer().getScheduler().scheduleSyncRepeatingTask(
      plugin,
      new DynamicMenuScheduler(registry),
      0L,
      20
    );
  }
}
