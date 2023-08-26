package dev.emmily.daisy.menu.types.chest;

import dev.emmily.daisy.item.MenuItem;
import dev.emmily.daisy.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryCloseEvent;
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
                   Predicate<InventoryOpenEvent> openAction,
                   Consumer<InventoryCloseEvent> closeAction) {
    super(title, size, items, Type.CHEST, openAction, closeAction);
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
