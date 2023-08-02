package dev.emmily.daisy;

import dev.emmily.daisy.listeners.InventoryClickListener;
import dev.emmily.daisy.listeners.InventoryOpenListener;
import dev.emmily.daisy.protocol.NbtHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class DaisySetup {
  private static final Constructor<?> NBT_HANDLER_CONSTRUCTOR;
  public static final NbtHandler NBT_HANDLER;
  private static final String PROTOCOL_CLASS_PATTERN = "dev.emmily.daisy.protocol.%s";
  private static final String SERVER_VERSION = Bukkit
    .getServer()
    .getClass()
    .getName()
    .split("\\.")[3];

  static {
    try {
      NBT_HANDLER_CONSTRUCTOR = Class
        .forName(String.format(PROTOCOL_CLASS_PATTERN, SERVER_VERSION + ".NbtHandlerImpl"))
        .getConstructor();
      NBT_HANDLER = (NbtHandler) NBT_HANDLER_CONSTRUCTOR.newInstance();
    } catch (ClassNotFoundException | NoSuchMethodException e) {
      throw new RuntimeException(String.format("your server version (%s) is not supported by Daisy", SERVER_VERSION));
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public static void injectListeners(Plugin plugin) {
    PluginManager pluginManager = plugin.getServer().getPluginManager();

    pluginManager.registerEvents(new InventoryClickListener(), plugin);
    pluginManager.registerEvents(new InventoryOpenListener(plugin), plugin);
  }
}
