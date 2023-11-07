package dev.emmily.daisy.core.listeners;

import dev.emmily.daisy.api.menu.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

import java.util.function.Predicate;

public class InventoryDragListener
  implements Listener {
  @EventHandler
  public void onInventoryDrag(InventoryDragEvent event) {
    Inventory inventory = event.getInventory();

    if (inventory.getHolder() instanceof Menu) {
      Predicate<InventoryDragEvent> dragAction = ((Menu) inventory.getHolder()).getDragAction();

      if (dragAction != null) {
        event.setCancelled(dragAction.test(event));
      }
    }
  }
}
