package dev.emmily.daisy.listeners;

import dev.emmily.daisy.item.MenuItem;
import dev.emmily.daisy.menu.Menu;
import dev.emmily.daisy.action.Action;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class InventoryClickListener
  implements Listener {
  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    Inventory inventory = event.getInventory();
    HumanEntity entity = event.getWhoClicked();
    int slot = event.getSlot();

    if (!(entity instanceof Player)) {
      return;
    }

    // Out of bounds click
    if (slot < 0 || slot > inventory.getSize()) {
      return;
    }

    if (!(inventory.getHolder() instanceof Menu)) {
      return;
    }

    Menu menu = (Menu) inventory.getHolder();
    List<MenuItem> items = menu.getItems();

    if (items.size() < slot) {
      return;
    }

    MenuItem item = items.get(slot);
    if (item == null) {
      return;
    }

    Action clickAction = item.getAction();
    event.setCancelled(clickAction.perform(event.getWhoClicked()));
  }
}
