package dev.emmily.daisy.api.protocol.title;

import dev.emmily.daisy.api.menu.Menu;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

import static dev.emmily.daisy.api.protocol.ServerVersion.PROTOCOL_CLASS_PATTERN;
import static dev.emmily.daisy.api.protocol.ServerVersion.SERVER_VERSION;

public interface TitleUpdater {
  void updateTitle(Player player,
                   Menu menu,
                   String newTitle);

  class Holder {
    private static final Object LOCK = new Object();
    private static volatile TitleUpdater instance;

    public static TitleUpdater getInstance() {
      if (instance == null) {
        synchronized (LOCK) {
          if (instance == null) {
            try {
              instance = (TitleUpdater) Class
                .forName(String.format(PROTOCOL_CLASS_PATTERN, SERVER_VERSION + ".NbtHandlerImpl"))
                .getConstructor()
                .newInstance();
            } catch (ClassNotFoundException | NoSuchMethodException e) {
              throw new RuntimeException(String.format("your server version (%s) is not supported by Daisy", SERVER_VERSION));
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
              throw new RuntimeException(e);
            }
          }
        }
      }

      return instance;
    }
  }
}
