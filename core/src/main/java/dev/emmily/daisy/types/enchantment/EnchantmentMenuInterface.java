package dev.emmily.daisy.types.enchantment;

import dev.emmily.daisy.MenuInterface;
import dev.emmily.daisy.action.Action;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class EnchantmentMenuInterface
  extends MenuInterface {
  private final List<String> options;
  public EnchantmentMenuInterface(String title,
                                  Action openAction,
                                  List<String> options) {
    super(title, 4, Type.ENCHANTMENT_TABLE, openAction);
    this.options = options;
  }

  public List<String> getOptions() {
    return options;
  }

  @Override
  public Inventory getInventory() {
    return Bukkit.createInventory(this, InventoryType.ENCHANTING, getTitle());
  }
}
