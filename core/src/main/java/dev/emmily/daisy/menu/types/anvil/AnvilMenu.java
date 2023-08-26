package dev.emmily.daisy.menu.types.anvil;

import dev.emmily.daisy.item.MenuItem;
import dev.emmily.daisy.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class AnvilMenu
  extends Menu {
  private final Predicate<InventoryClickEvent> confirmAction;
  private final Predicate<InventoryClickEvent> cancelAction;
  private final Inventory inventory;

  public AnvilMenu(String title,
                   List<MenuItem> items,
                   Predicate<InventoryOpenEvent> openAction,
                   Consumer<InventoryCloseEvent> closeAction,
                   Predicate<InventoryClickEvent> confirmAction,
                   Predicate<InventoryClickEvent> cancelAction) {
    super(title, 3, items, Type.ANVIL, openAction, closeAction);
    this.confirmAction = confirmAction;
    this.cancelAction = cancelAction;
    this.inventory = Bukkit.createInventory(this, InventoryType.ANVIL, getTitle());
  }

  public Predicate<InventoryClickEvent> getConfirmAction() {
    return confirmAction;
  }

  public Predicate<InventoryClickEvent> getCancelAction() {
    return cancelAction;
  }

  @Override
  public Inventory getInventory() {
    return inventory;
  }
}
