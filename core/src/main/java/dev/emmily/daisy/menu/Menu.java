package dev.emmily.daisy.menu;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dev.emmily.daisy.item.MenuItem;
import dev.emmily.daisy.action.Action;
import dev.emmily.daisy.menu.types.chest.ChestMenuBuilder;
import dev.emmily.daisy.menu.types.layout.LayoutMenuBuilder;
import dev.emmily.daisy.menu.types.paginated.PaginatedMenuBuilder;
import org.bukkit.inventory.InventoryHolder;

import java.beans.ConstructorProperties;
import java.util.List;

/**
 * Represents a wrapped Minecraft inventory.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public abstract class Menu
  implements InventoryHolder {
  public static ChestMenuBuilder chestMenuBuilder() {
    return new ChestMenuBuilder();
  }
  public static LayoutMenuBuilder layoutMenuBuilder() {
    return new LayoutMenuBuilder();
  }
  public static <T> PaginatedMenuBuilder<T> paginatedMenuBuilder() {
    return new PaginatedMenuBuilder<>();
  }

  protected final String title;
  protected final int size;
  private final List<MenuItem> items;
  private final Type type;
  private final Action openAction;
  private final Action closeAction;
  @ConstructorProperties({
    "title", "size", "items",
    "type", "openAction", "closeAction"
  })
  public Menu(String title,
              int size,
              List<MenuItem> items,
              Type type,
              Action openAction,
              Action closeAction) {
    this.title = title;
    this.size = size;
    this.type = type;
    this.items = items;
    this.openAction = openAction;
    this.closeAction = closeAction;
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

  public Action getCloseAction() {
    return closeAction;
  }

  public List<MenuItem> getItems() {
    return items;
  }

  public enum Type {
    ANVIL,
    CHEST,
    ENCHANTMENT,
    LAYOUT,
    PAGINATED,
    UPDATABLE
  }
}
