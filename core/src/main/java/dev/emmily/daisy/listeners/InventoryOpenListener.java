package dev.emmily.daisy.listeners;

import dev.emmily.daisy.MenuInterface;
import dev.emmily.daisy.action.Action;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

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
    MenuInterface menuInterface;

    if (inventory instanceof MenuInterface) {
      menuInterface = (MenuInterface) inventory;
    } else if (inventory.getHolder() instanceof MenuInterface) {
      menuInterface = (MenuInterface) inventory.getHolder();
    } else {
      return;
    }

    Action openAction = menuInterface.getOpenAction();

    if (!openAction.perform(player)) {
      event.setCancelled(true);

      return;
    }

    player.openInventory(inventory);
  }
}
