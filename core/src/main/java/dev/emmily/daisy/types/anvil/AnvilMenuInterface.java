package dev.emmily.daisy.types.anvil;

import dev.emmily.daisy.MenuInterface;
import dev.emmily.daisy.action.Action;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class AnvilMenuInterface
  extends MenuInterface {
  private final Action confirmAction;
  private final Action cancelAction;

  public AnvilMenuInterface(String title,
                            Action openAction,
                            Action confirmAction,
                            Action cancelAction) {
    super(title, 3, Type.ANVIL, openAction);
    this.confirmAction = confirmAction;
    this.cancelAction = cancelAction;
  }

  public Action getConfirmAction() {
    return confirmAction;
  }

  public Action getCancelAction() {
    return cancelAction;
  }

  @Override
  public Inventory getInventory() {
    return Bukkit.createInventory(this, InventoryType.ANVIL, getTitle());
  }
}
