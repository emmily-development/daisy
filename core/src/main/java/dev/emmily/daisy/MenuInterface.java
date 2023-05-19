package dev.emmily.daisy;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dev.emmily.daisy.action.Action;
import dev.emmily.daisy.item.MenuItem;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.bukkit.inventory.InventoryHolder;

import java.beans.ConstructorProperties;

/**
 * Represents a wrapped Minecraft inventory.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public abstract class MenuInterface
  implements InventoryHolder {
  private final String title;
  private final int size;
  private final Int2ObjectMap<MenuItem> items;
  private final Type type;
  private final Action openAction;

  @ConstructorProperties({
    "title", "size", "items",
    "type", "openAction"
  })
  public MenuInterface(String title,
                       int size,
                       Int2ObjectMap<MenuItem> items,
                       Type type,
                       Action openAction) {
    this.title = title;
    this.size = size;
    this.type = type;
    this.items = items;
    this.openAction = openAction;
  }

  /**
   * Returns the Minecraft GUI's title.
   *
   * @return The Minecraft GUI's title.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Returns the Minecraft GUI's total
   * slots.
   *
   * @return the Minecraft GUI's total
   * slots.
   */
  public int getActualSize() {
    return size;
  }

  /**
   * Returns the interface's total
   * rows.
   *
   * @return The interface's total
   * rows.
   */
  public int getRows() {
    return size / 9;
  }

  /**
   * Returns the interface's Minecraft
   * GUI type.
   *
   * @return The interface's Minecraft
   * GUI type.
   */
  public Type getType() {
    return type;
  }

  /**
   * Returns the interface's open action,
   * nullable.
   *
   * @return The interface's open action,
   * nullable.
   */
  public Action getOpenAction() {
    return openAction;
  }

  public Int2ObjectMap<MenuItem> getItems() {
    return items;
  }

  /**
   * Represents the minecraft menu
   * wrapped by a {@link MenuInterface}.
   */
  public enum Type {
    /**
     * The most commonly-used interface.
     * Represents a Minecraft chest with
     * a fixed size <strong>(up to 6)</strong>.
     */
    CHEST,
    /**
     * Represents a Minecraft anvil, sometimes
     * used to get a player's input, although
     * it's not recommended by us.
     */
    ANVIL,
    /**
     * Represents a Minecraft enchantment
     * table, which allows you to make a
     * player choose between 3 options.
     */
    ENCHANTMENT_TABLE,
    /**
     * Represents a Minecraft furnace,
     * usually used to let players
     * confirm or cancel an operation.
     */
    FURNACE,
    /**
     * Similar to {@link #CHEST}, but it's
     * a player's inventory.
     */
    PLAYER_INVENTORY
  }
}
