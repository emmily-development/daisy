package dev.emmily.daisy.menu.types.builder;

import dev.emmily.daisy.item.MenuItem;
import dev.emmily.daisy.menu.Menu;
import dev.emmily.daisy.menu.types.chest.ChestSize;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class MenuBuilder<T extends Menu> {
  protected String title;
  protected int size;
  protected List<MenuItem> items;
  protected Menu.Type type;
  protected Predicate<InventoryOpenEvent> openAction;
  protected Consumer<InventoryCloseEvent> closeAction;

  public MenuBuilder<T> title(String title) {
    this.title = title;

    return this;
  }

  public MenuBuilder<T> size(int size) {
    this.size = size;

    return this;
  }

  public MenuBuilder<T> items(List<MenuItem> items) {
    this.items = items;

    return this;
  }

  public MenuBuilder<T> addItem(MenuItem item) {
    if (items == null) {
      items = new ArrayList<>(Collections.nCopies(ChestSize.toSlots(6), null));
    }

    items.set(item.getSlot(), item);

    return this;
  }

  public MenuBuilder<T> addItems(MenuItem... items) {
    if (this.items == null) {
      this.items = new ArrayList<>(Collections.nCopies(ChestSize.toSlots(6), null));
    }

    for (MenuItem item : items) {
      this.items.add(item.getSlot(), item);
    }

    return this;
  }

  public MenuBuilder<T> type(Menu.Type type) {
    this.type = type;

    return this;
  }

  public MenuBuilder<T> openAction(Predicate<InventoryOpenEvent> openAction) {
    this.openAction = openAction;

    return this;
  }

  public MenuBuilder<T> closeAction(Consumer<InventoryCloseEvent> closeAction) {
    this.closeAction = closeAction;

    return this;
  }

  public abstract T build();
}
