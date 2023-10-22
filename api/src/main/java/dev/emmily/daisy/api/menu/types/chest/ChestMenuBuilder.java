package dev.emmily.daisy.api.menu.types.chest;

import com.google.common.base.Preconditions;
import dev.emmily.daisy.api.item.MenuItem;
import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.menu.types.builder.MenuBuilder;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ChestMenuBuilder
  extends MenuBuilder<ChestMenu> {
  @Override
  public ChestMenuBuilder title(String title) {
    this.title = title;

    return this;
  }

  @Override
  public ChestMenuBuilder size(int size) {
    this.size = size;

    return this;
  }

  public ChestMenuBuilder items(List<MenuItem> items) {
    super.items = items;

    return this;
  }

  public ChestMenuBuilder addItem(MenuItem item) {
    if (super.items == null) {
      super.items = new ArrayList<>();
    }

    super.items.add(item);

    return this;
  }

  public ChestMenuBuilder addItems(List<MenuItem> items) {
    if (this.items == null) {
      this.items = new ArrayList<>();
    }

    this.items.addAll(items);

    return this;
  }

  public ChestMenuBuilder addItems(MenuItem... items) {
    return addItems(Arrays.asList(items));
  }

  public ChestMenuBuilder type(List<Menu.Type> type) {
    this.type = type;

    return this;
  }

  public ChestMenuBuilder addType(Menu.Type type) {
    if (this.type == null) {
      this.type = new ArrayList<>();
    }

    this.type.add(type);

    return this;
  }

  public ChestMenuBuilder addTypes(List<Menu.Type> types) {
    if (type == null) {
      type = new ArrayList<>();
    }

    type.addAll(types);

    return this;
  }

  public ChestMenuBuilder addTypes(Menu.Type... types) {
    return addTypes(Arrays.asList(types));
  }

  public ChestMenuBuilder bukkitType(InventoryType bukkitType) {
    this.bukkitType = bukkitType;

    return this;
  }

  public ChestMenuBuilder openAction(Predicate<InventoryOpenEvent> openAction) {
    this.openAction = openAction;

    return this;
  }

  public ChestMenuBuilder closeAction(Consumer<InventoryCloseEvent> closeAction) {
    this.closeAction = closeAction;

    return this;
  }

  public ChestMenuBuilder blockClicks(boolean blockClicks) {
    this.blockClicks = blockClicks;

    return this;
  }

  @Override
  public ChestMenu build() {
    checkPreconditions();
    Preconditions.checkArgument(size % 9 == 0, "invalid size for a chest menu: %s", size);

    return new ChestMenu(
      title, items,
      size, type, openAction,
      closeAction, blockClicks
    );
  }
}
