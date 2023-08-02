package dev.emmily.daisy.menu.types.anvil;

import dev.emmily.daisy.item.MenuItem;
import dev.emmily.daisy.menu.Menu;
import dev.emmily.daisy.action.Action;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class AnvilMenu
  extends Menu {
  private final Action confirmAction;
  private final Action cancelAction;
  private final Inventory inventory;

  public AnvilMenu(String title,
                   List<MenuItem> items,
                   Action openAction,
                   Action closeAction,
                   Action confirmAction,
                   Action cancelAction) {
    super(title, 3, items, Type.ANVIL, openAction, closeAction);
    this.confirmAction = confirmAction;
    this.cancelAction = cancelAction;
    this.inventory = Bukkit.createInventory(this, InventoryType.ANVIL, getTitle());
  }

  public Action getConfirmAction() {
    return confirmAction;
  }

  public Action getCancelAction() {
    return cancelAction;
  }

  @Override
  public Inventory getInventory() {
    return inventory;
  }
}
