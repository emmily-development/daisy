package dev.emmily.daisydemo.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigurationFile extends YamlConfiguration {
  private final String filename;
  private final File file;
  private final Plugin plugin;

  public ConfigurationFile(
    Plugin plugin,
    String filename,
    String fileExtension,
    File folder
  ) {
    this.plugin = plugin;
    this.filename = filename + (filename.endsWith(fileExtension) ? "" : fileExtension);
    this.file = new File(folder, this.filename);
    this.createFile();
  }

  public ConfigurationFile(
    Plugin plugin,
    String fileName
  ) {
    this(plugin, fileName, ".yml");
  }

  public ConfigurationFile(
    Plugin plugin,
    String fileName,
    String fileExtension
  ) {
    this(plugin, fileName, fileExtension, plugin.getDataFolder());
  }

  public ConfigurationFile(
    Plugin plugin,
    String fileName,
    String fileExtension,
    String filePath
  ) {
    this(plugin, fileName, fileExtension, new File(plugin.getDataFolder().getAbsolutePath() + "/" + filePath));
  }

  private void createFile() {
    try {
      if (!file.exists()) {
        if (plugin.getResource(filename) != null) {
          plugin.saveResource(filename, false);
        } else {
          save(file);
        }

        load(file);

        return;
      }

      load(file);
      save(file);
    } catch (InvalidConfigurationException | IOException e) {
      e.printStackTrace();
    }
  }

  public void reload() {
    try {
      load(file);
    } catch (IOException | InvalidConfigurationException e) {
      e.printStackTrace();
    }
  }

  public void save() {
    try {
      save(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getString(String path) {
    return ChatColor.translateAlternateColorCodes('&', super.getString(path));
  }

  @Override
  public String getString(
    String path,
    String def
  ) {
    return ChatColor.translateAlternateColorCodes('&', super.getString(path, def));
  }

  @Override
  public List<String> getStringList(String path) {
    List<String> result = super.getStringList(path);
    result.replaceAll(string -> ChatColor.translateAlternateColorCodes('&', string));

    return result;
  }
}
