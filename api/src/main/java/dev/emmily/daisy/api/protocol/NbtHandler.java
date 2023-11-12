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
   * Returns the {@code byte} corresponding
   * to the given {@code key}.
   *
   * @param item The item to get the
   *             byte from.
   * @param key  The key of the byte.
   * @return The {@code byte} corresponding
   * to the given {@code key}.
   */
  byte getByte(ItemStack item,
               String key);

  /**
   * Returns the {@code short} corresponding
   * to the given {@code key}.
   *
   * @param item The item to get the
   *             short from.
   * @param key  The key of the short.
   * @return The {@code short} corresponding
   * to the given {@code key}.
   */
  short getShort(ItemStack item,
                 String key);

  /**
   * Returns the {@code int} corresponding
   * to the given {@code key}.
   *
   * @param item The item to get the
   *             int from.
   * @param key  The key of the int.
   * @return The {@code int} corresponding
   * to the given {@code key}.
   */
  int getInt(ItemStack item,
             String key);

  /**
   * Returns the {@code long} corresponding
   * to the given {@code key}.
   *
   * @param item The item to get the
   *             long from.
   * @param key  The key of the long.
   * @return The {@code long} corresponding
   * to the given {@code key}.
   */
  long getLong(ItemStack item,
               String key);

  /**
   * Returns the {@code float} corresponding
   * to the given {@code key}.
   *
   * @param item The item to get the
   *             float from.
   * @param key  The key of the float.
   * @return The {@code float} corresponding
   * to the given {@code key}.
   */
  float getFloat(ItemStack item,
                 String key);

  /**
   * Returns the {@code double} corresponding
   * to the given {@code key}.
   *
   * @param item The item to get the
   *             double from.
   * @param key  The key of the double.
   * @return The {@code double} corresponding
   * to the given {@code key}.
   */
  double getDouble(ItemStack item,
                   String key);

  /**
   * Returns the {@code byte array} corresponding
   * to the given {@code key}.
   *
   * @param item The item to get the
   *             byte array from.
   * @param key  The key of the byte array.
   * @return The {@code byte array} corresponding
   * to the given {@code key}.
   */
  byte[] getByteArray(ItemStack item,
                      String key);

  /**
   * Returns the {@code String} corresponding
   * to the given {@code key}.
   *
   * @param item The item to get the
   *             String from.
   * @param key  The key of the String.
   * @return The {@code String} corresponding
   * to the given {@code key}.
   */
  String getString(ItemStack item,
                   String key);

  /**
   * Returns the {@code int[]} corresponding
   * to the given {@code key}.
   *
   * @param item The item to get the
   *             int[] from.
   * @param key  The key of the int[].
   * @return The {@code int[]} corresponding
   * to the given {@code key}.
   */
  int[] getIntArray(ItemStack item,
                    String key);

  /**
   * Removes the tag corresponding to
   * the given {@code key} from the
   * given {@code item}.
   *
   * @param item The item to remove
   *             the tag from.
   * @param key  The key of the tag.
   */
  ItemStack removeTag(ItemStack item,
                      String key);

  /**
   * Returns the list of all the
   * NBT tags of the given {@code item}.
   *
   * @param item The items to get the tags
   *             from.
   * @return The list of all the
   * NBT tags of the given {@code item}
   * @deprecated Causes magic values to appear.
   */
  @Deprecated
  Map<String, Object> getTags(ItemStack item);

  public class Holder {
    private static final Object LOCK = new Object();
    private static final String PROTOCOL_CLASS_PATTERN = "dev.emmily.daisy.protocol.%s";
    private static final String SERVER_VERSION = Bukkit
      .getServer()
      .getClass()
      .getName()
      .split("\\.")[3];
    private static volatile NbtHandler instance;

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
