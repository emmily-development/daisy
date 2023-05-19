package dev.emmily.daisy.types.chest;

import dev.emmily.daisy.MenuInterface;
import dev.emmily.daisy.action.Action;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class ChestMenuInterface
  extends MenuInterface {
  public ChestMenuInterface(String title, int size, Action openAction) {
    super(title, size, Type.CHEST, openAction);
  }

  @Override
  public Inventory getInventory() {
    return Bukkit.createInventory(this, getActualSize(), getTitle());
  }
}
