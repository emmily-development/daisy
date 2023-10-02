package dev.emmily.daisy.core.listeners;

import dev.emmily.daisy.api.item.MenuItem;
import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.menu.types.dynamic.AbstractDynamicMenu;
import dev.emmily.daisy.api.menu.types.paginated.PaginatedMenu;
import dev.emmily.daisy.api.protocol.NbtHandler;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.function.Predicate;

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

    if (menu instanceof AbstractDynamicMenu) {
      event.setCancelled(true);

      return;
    }

    List<MenuItem> items = menu.getItems();

    for (MenuItem item : items) {
      if (item.getSlot() == slot) {
        Predicate<InventoryClickEvent> clickAction = item.getAction();
        event.setCancelled(clickAction.test(event));
      }
    }

    if (menu instanceof PaginatedMenu) {
      PaginatedMenu<?> paginatedMenu = (PaginatedMenu<?>) menu;

      if (slot == paginatedMenu.getPreviousPageSwitch().getSlot() && paginatedMenu.hasPreviousPage()) {
        paginatedMenu.render(PaginatedMenu.PageOperand.PREVIOUS);
      } else if (slot == paginatedMenu.getNextPageSwitch().getSlot() && paginatedMenu.hasNextPage()) {
        paginatedMenu.render(PaginatedMenu.PageOperand.NEXT);
      }
    }
  }
}
