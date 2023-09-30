package dev.emmily.daisy.api.menu.reader.loader;

import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.menu.reader.MenuReader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MenuLoader {
  private final JavaPlugin plugin;
  private final File menusFolder;
  private final MenuReader menuReader;
  private final Map<String, Menu> menus;

  public MenuLoader(JavaPlugin plugin,
                    MenuReader menuReader) {
    this.plugin = plugin;
    this.menusFolder = new File(plugin.getDataFolder(), "menus");

    if (!menusFolder.exists() && !menusFolder.mkdir()) {
      plugin.getLogger().warning("Unable to create the menus folder");
    }

    this.menus = new HashMap<>();
    this.menuReader = menuReader;
  }

  public Menu load(String filename,
                   String resourceName) throws IOException {
    return loadFromFile(new File(menusFolder, filename), resourceName);
  }

  private Menu loadFromFile(File file,
                            String resourceName) throws IOException {
    if (!file.exists()) {
      ResourceLoader.load(plugin, file, resourceName);

      if (!file.exists()) {
        return null;
      }
    }

    Menu menu = menuReader.readFromFile(file);
    menus.put(file.getName(), menu);

    return menu;
  }
}
