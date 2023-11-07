package dev.emmily.daisy.api.menu.types.dynamic;

import dev.emmily.daisy.api.item.MenuItem;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class DynamicMenu
  extends AbstractDynamicMenu<MenuItem> {
  private final Inventory inventory;
  private final Consumer<DynamicMenu> updateAction;

  public DynamicMenu(String title,
                     int size,
                     List<Type> type,
                     Predicate<InventoryOpenEvent> openAction,
                     Consumer<InventoryCloseEvent> closeAction,
                     Predicate<InventoryDragEvent> dragAction,
                     Predicate<InventoryClickEvent> unknownSlotClickAction,
                     long updatePeriod,
                     List<List<MenuItem>> frames,
                     List<MenuItem> staticItems,
                     List<Supplier<MenuItem>> suppliableItems,
                     Consumer<DynamicMenu> updateAction,
                     InventoryType bukkitType) {
    super(
      title, size, type,
      openAction, closeAction,
      dragAction, unknownSlotClickAction,
      updatePeriod, frames, staticItems,
      suppliableItems, bukkitType
    );
    this.inventory = bukkitType ==
      InventoryType.CHEST || bukkitType == InventoryType.ENDER_CHEST
      ? Bukkit.createInventory(this, size, title)
      : Bukkit.createInventory(this, bukkitType, title);
    this.updateAction = updateAction;
    render();
  }

  @Override
  public Consumer<List<MenuItem>> getRenderer() {
    return menuItems -> {
      getItems().addAll(menuItems);

      for (MenuItem item : getItems()) {
        if (item == null) {
          continue;
        }

        inventory.setItem(item.getSlot(), item.getItem());
      }
    };
  }

  @Override
  public Inventory getInventory() {
    return inventory;
  }

  @Override
  public void update() {
    updateAction.accept(this);
  }
}
