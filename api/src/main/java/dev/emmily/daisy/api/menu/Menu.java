package dev.emmily.daisy.api.menu;

import dev.emmily.daisy.api.item.MenuItem;
import dev.emmily.daisy.api.menu.types.chest.ChestMenuBuilder;
import dev.emmily.daisy.api.menu.types.chest.ChestSize;
import dev.emmily.daisy.api.menu.types.dynamic.DynamicMenuBuilder;
import dev.emmily.daisy.api.menu.types.dynamic.layout.DynamicLayoutMenuBuilder;
import dev.emmily.daisy.api.menu.types.layout.LayoutMenuBuilder;
import dev.emmily.daisy.api.menu.types.paginated.PaginatedMenuBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Represents a wrapped Minecraft inventory.
 */
// @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public abstract class Menu
  implements InventoryHolder {
  public static ChestMenuBuilder chestMenuBuilder() {
    return new ChestMenuBuilder()
      .addType(Type.CHEST);
  }

  public static LayoutMenuBuilder layoutMenuBuilder() {
    return new LayoutMenuBuilder()
      .addType(Type.LAYOUT);
  }

  public static DynamicMenuBuilder dynamicMenuBuilder() {
    return new DynamicMenuBuilder()
      .addType(Type.DYNAMIC);
  }

  public static DynamicLayoutMenuBuilder dynamicLayoutMenuBuilder() {
    return new DynamicLayoutMenuBuilder()
      .addTypes(Type.DYNAMIC, Type.LAYOUT);
  }

  public static <T> PaginatedMenuBuilder<T> paginatedMenuBuilder() {
    return new PaginatedMenuBuilder<T>()
      .addType(Type.PAGINATED);
  }
  
  protected final String title;
  protected final int size;
  private final List<MenuItem> items;
  private final List<Type> type;
  private final Predicate<InventoryOpenEvent> openAction;
  private final Consumer<InventoryCloseEvent> closeAction;
  private final Predicate<InventoryDragEvent> dragAction;
  private final Predicate<InventoryClickEvent> unknownSlotClickAction;
  /**
   * Creates a menu with the given
   * parameters.
   *
   * @param title                  The title of the menu.
   * @param size                   The size of the menu.
   * @param items                  The items contained in
   *                               the menu.
   * @param type                   The types of the menu.
   * @param openAction             The action executed
   *                               when the inventory
   *                               is opened.
   * @param closeAction            The action executed
   *                               when the inventory
   *                               is closed
   * @param dragAction             The action executed when
   *                               items are dragged to the
   *                               menu.
   * @param unknownSlotClickAction The action performed when
   *                               an unknown slot is clicked.
   */
  public Menu(String title,
              int size,
              List<MenuItem> items,
              List<Type> type,
              Predicate<InventoryOpenEvent> openAction,
              Consumer<InventoryCloseEvent> closeAction,
              Predicate<InventoryDragEvent> dragAction,
              Predicate<InventoryClickEvent> unknownSlotClickAction) {
    this.title = title;
    this.type = type;
    this.unknownSlotClickAction = unknownSlotClickAction;
    int maxRows = findMaxRows();
    if (size < 9 && (maxRows == 3 || maxRows == 6)) {
      this.size = ChestSize.toSlots(size);
    } else {
      this.size = size;
    }
    this.items = items;
    this.openAction = openAction;
    this.closeAction = closeAction;
    this.dragAction = dragAction;
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
   * Returns the menu's total
   * rows.
   *
   * @return The menu's total
   * rows.
   */
  public int getRows() {
    return size / 9;
  }

  /**
   * Returns the menu's Minecraft
   * GUI type.
   *
   * @return The menu's Minecraft
   * GUI type.
   */
  public List<Type> getType() {
    return type;
  }

  /**
   * Returns the menu's open action,
   * nullable.
   *
   * @return The menu's open action,
   * nullable.
   */
  public Predicate<InventoryOpenEvent> getOpenAction() {
    return openAction;
  }

  /**
   * Returns the menu's close action,
   * nullable.
   *
   * @return The menu's close action,
   * nullable.
   */
  public Consumer<InventoryCloseEvent> getCloseAction() {
    return closeAction;
  }

  public Predicate<InventoryDragEvent> getDragAction() {
    return dragAction;
  }

  public Predicate<InventoryClickEvent> getUnknownSlotClickAction() {
    return unknownSlotClickAction;
  }

  public List<MenuItem> getItems() {
    return items;
  }

  public int findMaxRows() {
    for (Type type : this.type) {
      int maxRows = type.maxRows;

      if (maxRows == 0) {
        continue;
      }

      return maxRows;
    }

    throw new IllegalArgumentException("The given menu types are not Minecraft inventory types");
  }

  public int findMaxRowSize() {
    for (Type type : this.type) {
      int maxRowSize = type.maxRowSize;

      if (maxRowSize == 0) {
        continue;
      }

      return maxRowSize;
    }

    throw new IllegalArgumentException("The given menu types are not Minecraft inventory types");
  }

  public void open(Player player) {
    player.openInventory(getInventory());
  }

  public enum Type {
    CHEST(6, 9),
    ENDER_CHEST(3, 9),
    ANVIL(1, 3),
    HOPPER(1, 5),
    PLAYER(4, 9),
    LAYOUT(0, 0),
    PAGINATED(0, 0),
    DYNAMIC(0, 0);

    final int maxRows;
    final int maxRowSize;

    Type(int maxRows,
         int maxRowSize) {
      this.maxRows = maxRows;
      this.maxRowSize = maxRowSize;
    }

    public int getMaxRows() {
      return maxRows;
    }

    public int getMaxRowSize() {
      return maxRowSize;
    }

    public int getMaxSlots() {
      return maxRows * maxRowSize;
    }

    public boolean isMinecraftType() {
      return maxRows != 0 || maxRowSize != 0;
    }
  }

}
