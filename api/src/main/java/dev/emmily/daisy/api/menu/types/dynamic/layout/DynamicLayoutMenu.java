package dev.emmily.daisy.api.menu.types.dynamic.layout;

import com.google.common.base.Preconditions;
import dev.emmily.daisy.api.item.MenuItem;
import dev.emmily.daisy.api.menu.types.dynamic.AbstractDynamicMenu;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class DynamicLayoutMenu
  extends AbstractDynamicMenu<String> {
  private final Inventory inventory;
  private final Map<Character, MenuItem> itemsByKey;
  private final Consumer<DynamicLayoutMenu> updateAction;

  public DynamicLayoutMenu(String title,
                           int size,
                           List<Type> type,
                           Predicate<InventoryOpenEvent> openAction,
                           Consumer<InventoryCloseEvent> closeAction,
                           long updatePeriod,
                           List<List<String>> frames,
                           List<MenuItem> staticItems,
                           List<Supplier<MenuItem>> suppliableItems,
                           Consumer<DynamicLayoutMenu> updateAction,
                           Map<Character, MenuItem> itemsByKey,
                           InventoryType bukkitType,
                           boolean blockClicks) {
    super(
      title, size, type,
      openAction, closeAction,
      updatePeriod, frames,
      staticItems, suppliableItems,
      bukkitType, blockClicks
    );
    this.inventory = bukkitType ==
      InventoryType.CHEST || bukkitType == InventoryType.ENDER_CHEST
      ? Bukkit.createInventory(this, size, title)
      : Bukkit.createInventory(this, bukkitType, title);
    this.itemsByKey = itemsByKey;
    this.updateAction = updateAction;
    render();
  }

  @Override
  public Consumer<List<String>> getRenderer() {
    return layout -> {
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
          MenuItem item = itemsByKey.get(key);

          if (item == null) {
            index++;

            continue;
          }

          item = item.copy();
          item.setSlot(index);
          getItems().add(item);
          getInventory().setItem(index, item.getItem());

          index++;
        }
      }
    };
  }

  @Override
  public Inventory getInventory() {
    return inventory;
  }

  @Override
  public void update() {
    updateAction.accept(this);
  }
}
