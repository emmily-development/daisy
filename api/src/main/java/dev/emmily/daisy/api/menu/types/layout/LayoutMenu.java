package dev.emmily.daisy.api.menu.types.layout;

import com.google.common.base.Preconditions;
import dev.emmily.daisy.api.item.MenuItem;
import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.menu.types.chest.ChestSize;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class LayoutMenu
  extends Menu {
  private final List<String> layout;
  private final Map<Character, MenuItem> items;
  private final Inventory inventory;

  public LayoutMenu(String title,
                    List<Type> type,
                    Predicate<InventoryOpenEvent> openAction,
                    Consumer<InventoryCloseEvent> closeAction,
                    List<String> layout,
                    Map<Character, MenuItem> items,
                    InventoryType bukkitType,
                    boolean blockClicks) {
    super(
      title, ChestSize.toSlots(layout.size()),
      new ArrayList<>(), type,
      openAction, closeAction,
      blockClicks
    );
    this.layout = layout;
    this.items = items;
    this.inventory = bukkitType ==
      InventoryType.CHEST || bukkitType == InventoryType.ENDER_CHEST
      ? Bukkit.createInventory(this, size, title)
      : Bukkit.createInventory(this, bukkitType, title);

    int maxRows = findMaxRows();
    Preconditions.checkArgument(
      layout.size() <= maxRows,
      "the maximum number of rows is %s",
      maxRows
    );

    int maxRowSize = findMaxRowSize();

    int index = 0;
    for (String line : layout) {
      Preconditions.checkArgument(
        line.length() <= maxRowSize,
        "the maximum size for a line is %s",
        maxRowSize
      );

      for (char key : line.toCharArray()) {
        MenuItem item = items.get(key);

        if (item == null) {
          index++;

          continue;
        }

        item = item.copy();

        item.setSlot(index);
        getItems().add(item);
        inventory.setItem(index, item.getItem());

        index++;
      }
    }
  }

  public List<String> getLayout() {
    return layout;
  }

  public Map<Character, MenuItem> getLayoutItems() {
    return items;
  }

  @Override
  public Inventory getInventory() {
    return inventory;
  }
}
