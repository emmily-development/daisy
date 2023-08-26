package dev.emmily.daisy.menu;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dev.emmily.daisy.item.MenuItem;
import dev.emmily.daisy.menu.types.chest.ChestMenuBuilder;
import dev.emmily.daisy.menu.types.chest.ChestSize;
import dev.emmily.daisy.menu.types.layout.LayoutMenuBuilder;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

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

  protected final String title;
  protected final int size;
  private final List<MenuItem> items;
  private final Type type;
  private final Predicate<InventoryOpenEvent> openAction;
  private final Consumer<InventoryCloseEvent> closeAction;

  @ConstructorProperties({
    "title", "size", "items",
    "type", "openAction",
    "closeAction"
  })
  public Menu(String title,
              int size,
              List<MenuItem> items,
              Type type,
              Predicate<InventoryOpenEvent> openAction,
              Consumer<InventoryCloseEvent> closeAction) {
    this.title = title;
    if (size < 9 && type.maxRows == 6) {
      this.size = ChestSize.toSlots(size);
    } else {
      this.size = size;
    }
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
  public int getSize() {
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
   * Returns the interface's open Predicate,
   * nullable.
   *
   * @return The interface's open Predicate,
   * nullable.
   */
  public Predicate<InventoryOpenEvent> getOpenAction() {
    return openAction;
  }

  public Consumer<InventoryCloseEvent> getCloseAction() {
    return closeAction;
  }

  public List<MenuItem> getItems() {
    return items;
  }

  public enum Type {
    ANVIL(3),
    CHEST(6),
    ENCHANTMENT(2),
    LAYOUT(6),
    PAGINATED(6),
    UPDATABLE(6);

    final int maxRows;

    Type(int maxRows) {
      this.maxRows = maxRows;
    }

    public int getMaxRows() {
      return maxRows;
    }
  }
}
