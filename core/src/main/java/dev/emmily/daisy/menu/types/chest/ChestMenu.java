package dev.emmily.daisy.menu.types.chest;

import dev.emmily.daisy.item.MenuItem;
import dev.emmily.daisy.menu.Menu;
import dev.emmily.daisy.action.Action;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class ChestMenu
  extends Menu {
  private final Inventory inventory;

  public ChestMenu(String title,
                   List<MenuItem> items,
                   int size,
                   Action openAction,
                   Action closeAction) {
    super(title, size, items, Type.CHEST, openAction, closeAction);
    this.inventory = Bukkit.createInventory(this, getActualSize(), getTitle());

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
