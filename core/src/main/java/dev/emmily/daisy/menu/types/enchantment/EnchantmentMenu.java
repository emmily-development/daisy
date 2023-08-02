package dev.emmily.daisy.menu.types.enchantment;

import dev.emmily.daisy.item.MenuItem;
import dev.emmily.daisy.menu.Menu;
import dev.emmily.daisy.action.Action;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class EnchantmentMenu
  extends Menu {
  private final List<String> options;
  private final Inventory inventory;

  public EnchantmentMenu(String title,
                         List<MenuItem> items,
                         Action openAction,
                         Action closeAction,
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
