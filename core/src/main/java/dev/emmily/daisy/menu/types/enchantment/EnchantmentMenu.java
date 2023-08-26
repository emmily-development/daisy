package dev.emmily.daisy.menu.types.enchantment;

import dev.emmily.daisy.item.MenuItem;
import dev.emmily.daisy.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EnchantmentMenu
  extends Menu {
  private final List<String> options;
  private final Inventory inventory;

  public EnchantmentMenu(String title,
                         List<MenuItem> items,
                         Predicate<InventoryOpenEvent> openAction,
                         Consumer<InventoryCloseEvent> closeAction,
                         List<String> options) {
    super(title, 5, items, Type.ENCHANTMENT, openAction, closeAction);
    this.options = options;
    this.inventory = Bukkit.createInventory(this, InventoryType.ENCHANTING, getTitle());
  }

  public List<String> getOptions() {
    return options;
  }

  @Override
  public Inventory getInventory() {
    return inventory;
  }
}
