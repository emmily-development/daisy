package dev.emmily.daisy.core.listeners;

import dev.emmily.daisy.api.item.MenuItem;
import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.menu.types.dynamic.AbstractDynamicMenu;
import dev.emmily.daisy.api.menu.types.paginated.PaginatedMenu;
import dev.emmily.daisy.api.util.TriConsumer;
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
    int slot = event.getRawSlot();

    if (!(entity instanceof Player)) {
      return;
    }

    Player player = (Player) event.getWhoClicked();

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

    boolean foundItem = false;

    for (MenuItem item : items) {
      if (item.getSlot() == slot) {
        foundItem = true;
        Predicate<InventoryClickEvent> clickAction = item.getAction();

        if (clickAction != null) {
          event.setCancelled(clickAction.test(event));
        }

        break;
      }
    }

    if (!foundItem) {
      Predicate<InventoryClickEvent> unknownSlotClickAction = menu.getUnknownSlotClickAction();

      if (unknownSlotClickAction != null) {
        event.setCancelled(unknownSlotClickAction.test(event));
      }
    }

    if (menu instanceof PaginatedMenu) {
      PaginatedMenu<?> paginatedMenu = (PaginatedMenu<?>) menu;

      PaginatedMenu.PageOperand operand = null;
      if (slot == paginatedMenu.getPreviousPageSwitch().getSlot() && paginatedMenu.hasPreviousPage()) {
        operand = PaginatedMenu.PageOperand.PREVIOUS;
      } else if (slot == paginatedMenu.getNextPageSwitch().getSlot() && paginatedMenu.hasNextPage()) {
        operand = PaginatedMenu.PageOperand.NEXT;
      }

      if (operand != null) {
        int page = paginatedMenu.getCurrentPage();

        TriConsumer<Integer, Integer, PaginatedMenu.PageOperand> prePageSwitchAction = paginatedMenu.getPrePageSwitchAction();

        if (prePageSwitchAction != null) {
          prePageSwitchAction.accept(page, page + (operand == PaginatedMenu.PageOperand.NEXT ? 1 : -1), operand);
        }

        paginatedMenu.render(player, operand);

        TriConsumer<Integer, Integer, PaginatedMenu.PageOperand> postPageSwitchAction = paginatedMenu.getPostPageSwitchAction();

        if (postPageSwitchAction != null) {
          postPageSwitchAction.accept(page, paginatedMenu.getCurrentPage(), operand);
        }
      }
    }
  }
}
