package dev.emmily.daisy.api.menu.types.chest;

import dev.emmily.daisy.api.item.MenuItem;
import dev.emmily.daisy.api.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ChestMenu
  extends Menu {
  private final Inventory inventory;

  public ChestMenu(String title,
                   List<MenuItem> items,
                   int size,
                   List<Type> type,
                   Predicate<InventoryOpenEvent> openAction,
                   Consumer<InventoryCloseEvent> closeAction,
                   Predicate<InventoryDragEvent> dragAction,
                   Predicate<InventoryClickEvent> unknownSlotClickAction) {
    super(title, size, items, type, openAction, closeAction, dragAction, unknownSlotClickAction);
    this.inventory = Bukkit.createInventory(this, getSize(), getTitle());
    for (MenuItem item : getItems()) {
      if (item == null) {
        continue;
      }

      this.inventory.setItem(item.getSlot(), item.getItem());
    }
  }

  @Override
  public Inventory getInventory() {
    return inventory;
  }
}
