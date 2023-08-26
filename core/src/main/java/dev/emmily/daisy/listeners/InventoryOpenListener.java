package dev.emmily.daisy.listeners;

import dev.emmily.daisy.menu.Menu;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.function.Predicate;

public class InventoryOpenListener
  implements Listener {
  @EventHandler
  public void onInventoryOpen(InventoryOpenEvent event) {
    Inventory inventory = event.getInventory();
    HumanEntity entity = event.getPlayer();

    if (!(entity instanceof Player)) {
      return;
    }

    Player player = (Player) entity;

    if (!(inventory.getHolder() instanceof Menu)) {
      return;
    }

    Menu menu = (Menu) inventory.getHolder();
    Predicate<InventoryOpenEvent> openAction = menu.getOpenAction();

    if (!openAction.test(event)) {
      event.setCancelled(true);
    }
  }
}
