package dev.emmily.daisy.listeners;

import dev.emmily.daisy.menu.Menu;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class InventoryCloseListener
  implements Listener {
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent event) {
    Inventory inventory = event.getInventory();
    HumanEntity entity = event.getPlayer();

    if (!(entity instanceof Player)) {
      return;
    }

    if (!(inventory.getHolder() instanceof Menu)) {
      return;
    }

    Menu menu = (Menu) inventory.getHolder();
    Consumer<InventoryCloseEvent> closeAction = menu.getCloseAction();
    closeAction.accept(event);
  }
}
