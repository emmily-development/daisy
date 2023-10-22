package dev.emmily.daisy.api.menu.types.builder;

import com.google.common.base.Preconditions;
import dev.emmily.daisy.api.item.MenuItem;
import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.menu.types.chest.ChestSize;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class MenuBuilder<T extends Menu> {
  protected String title;
  protected int size;
  protected List<MenuItem> items = new ArrayList<>();
  protected List<Menu.Type> type;
  protected InventoryType bukkitType;
  protected Predicate<InventoryOpenEvent> openAction = event -> true;
  protected Consumer<InventoryCloseEvent> closeAction = event -> {};
  protected boolean blockClicks;

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
      items = new ArrayList<>();
    }

    items.add(item);

    return this;
  }

  public MenuBuilder<T> addItems(List<MenuItem> items) {
    if (this.items == null) {
      this.items = new ArrayList<>();
    }

    this.items.addAll(items);

    return this;
  }

  public MenuBuilder<T> addItems(MenuItem... items) {
    return addItems(Arrays.asList(items));
  }

  public MenuBuilder<T> type(List<Menu.Type> type) {
    this.type = type;

    return this;
  }

  public MenuBuilder<T> addType(Menu.Type type) {
    if (this.type == null) {
      this.type = new ArrayList<>();
    }

    this.type.add(type);

    return this;
  }

  public MenuBuilder<T> addTypes(List<Menu.Type> types) {
    if (type == null) {
      type = new ArrayList<>();
    }

    type.addAll(types);

    return this;
  }

  public MenuBuilder<T> addTypes(Menu.Type... types) {
    return addTypes(Arrays.asList(types));
  }

  public MenuBuilder<T> bukkitType(InventoryType bukkitType) {
    this.bukkitType = bukkitType;

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

  public MenuBuilder<T> blockClicks(boolean blockClicks) {
    this.blockClicks = blockClicks;

    return this;
  }

  public void checkPreconditions() {
    Preconditions.checkArgument(!type.isEmpty(), "no type given");
    Preconditions.checkNotNull(title, "title");

    if (size == 0) {
      size = findSize();
    }

    if (bukkitType == null) {
      bukkitType = InventoryType.CHEST;
    }
  }

  private int findSize() {
    for (Menu.Type type : type) {
      if (!type.isMinecraftType()) {
        continue;
      }

      return type.getMaxSlots();
    }

    throw new IllegalArgumentException("Invalid type");
  }

  public abstract T build();
}
