package dev.emmily.daisy.api.menu.reader.loader;

import com.google.common.io.Files;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface ResourceLoader {
  @SuppressWarnings("UnstableApiUsage")
  static void load(JavaPlugin plugin,
                   File file,
                   String name) throws IOException {
    if (!file.exists() && !file.createNewFile()) {
      return;
    }

    InputStream inputStream = plugin.getResource(name);
    byte[] buffer = new byte[inputStream.available()];
    inputStream.read(buffer);

    Files.write(buffer, file);
  }
}
