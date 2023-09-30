package dev.emmily.daisy.core.listeners;

import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.menu.types.dynamic.AbstractDynamicMenu;
import dev.emmily.daisy.api.menu.types.dynamic.DynamicMenu;
import dev.emmily.daisy.api.menu.types.dynamic.layout.DynamicLayoutMenu;
import dev.emmily.daisy.api.menu.types.dynamic.registry.DynamicMenuRegistry;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.function.Predicate;

public class InventoryOpenListener
  implements Listener {
  private final DynamicMenuRegistry dynamicMenuRegistry;

  public InventoryOpenListener(DynamicMenuRegistry dynamicMenuRegistry) {
    this.dynamicMenuRegistry = dynamicMenuRegistry;
  }

  @EventHandler
  public void onInventoryOpen(InventoryOpenEvent event) {
    Inventory inventory = event.getInventory();
    HumanEntity entity = event.getPlayer();

    if (!(entity instanceof Player)) {
      return;
    }

    if (!(inventory.getHolder() instanceof Menu)) {
      return;
    }


    Menu menu = (Menu) inventory.getHolder();

    if (menu instanceof DynamicMenu) {
      dynamicMenuRegistry.register((DynamicMenu) menu);
    } else if (menu instanceof DynamicLayoutMenu) {
      dynamicMenuRegistry.register((DynamicLayoutMenu) menu);
    }

    Predicate<InventoryOpenEvent> openAction = menu.getOpenAction();

    if (!openAction.test(event)) {
      event.setCancelled(true);
    }
  }
}
