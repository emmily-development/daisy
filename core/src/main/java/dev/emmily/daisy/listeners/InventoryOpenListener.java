package dev.emmily.daisy.listeners;

import dev.emmily.daisy.menu.Menu;
import dev.emmily.daisy.action.Action;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class InventoryOpenListener
  implements Listener {
  private final Plugin plugin;

  public InventoryOpenListener(Plugin plugin) {
    this.plugin = plugin;
  }

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
    Action openAction = menu.getOpenAction();

    if (!openAction.perform(player)) {
      event.setCancelled(true);
    }
  }
}
