package dev.emmily.daisy.api.protocol;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface NbtHandler {
  static NbtHandler getInstance() {
    return Holder.getInstance();
  }

  /**
   * Adds (or replaces if existing) an
   * NBT entry with the given {@code id}
   * and the given {@code value} to the
   * given {@code item}'s NBT tag.
   *
   * @param item  The item to add the tag to.
   * @param key   The key of the tag.
   * @param value The value of the tag.
   * @return The given {@code item} with the
   * applied NBT tag.
   */
  ItemStack addTag(ItemStack item,
                   String key,
                   Object value);

  /**
   * Returns {@code true} if the
   * given {@code item} has an NBT
   * entry with the given {@code id}
   * in its NBT tag.
   *
   * @param item The item to be checked.
   * @param key  The key to be checked.
   * @return {@code true} if the
   * given {@code item} has an
   * entry with the given {@code id}
   * in its NBT tag.
   */
  boolean hasTag(ItemStack item,
                 String key);

  /**
   * Returns the list of all the
   * NBT tags of the given {@code item}.
   * 
   * @param item The items to get the tags
   *             from.
   * @return The list of all the
   * NBT tags of the given {@code item}
   */
  Map<String, Object> getTags(ItemStack item);

  public class Holder {
    private static NbtHandler instance;
    private static final Object LOCK = new Object();
    private static final String PROTOCOL_CLASS_PATTERN = "dev.emmily.daisy.protocol.%s";
    private static final String SERVER_VERSION = Bukkit
      .getServer()
      .getClass()
      .getName()
      .split("\\.")[3];

    public static NbtHandler getInstance() {
      if (instance == null) {
        synchronized (LOCK) {
          if (instance == null) {
            try {
              instance = (NbtHandler) Class
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
