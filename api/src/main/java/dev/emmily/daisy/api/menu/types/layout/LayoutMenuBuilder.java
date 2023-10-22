package dev.emmily.daisy.api.menu.types.layout;

import com.google.common.base.Preconditions;
import dev.emmily.daisy.api.item.MenuItem;
import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.menu.types.chest.ChestSize;
import dev.emmily.daisy.api.menu.types.builder.MenuBuilder;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class LayoutMenuBuilder
  extends MenuBuilder<LayoutMenu> {
  private List<String> layout;
  private Map<Character, MenuItem> items;

  @Override
  public LayoutMenuBuilder title(String title) {
    this.title = title;

    return this;
  }

  @Override
  public LayoutMenuBuilder size(int size) {
    this.size = size;

    return this;
  }

  public LayoutMenuBuilder items(List<MenuItem> items) {
    super.items = items;

    return this;
  }

  public LayoutMenuBuilder addItem(MenuItem item) {
    if (super.items == null) {
      super.items = new ArrayList<>();
    }

    super.items.add(item);

    return this;
  }

  public LayoutMenuBuilder addItems(List<MenuItem> items) {
    if (super.items == null) {
      super.items = new ArrayList<>();
    }

    super.items.addAll(items);

    return this;
  }

  public LayoutMenuBuilder addItems(MenuItem... items) {
    return addItems(Arrays.asList(items));
  }

  public LayoutMenuBuilder type(List<Menu.Type> type) {
    this.type = type;

    return this;
  }

  public LayoutMenuBuilder addType(Menu.Type type) {
    if (this.type == null) {
      this.type = new ArrayList<>();
    }

    this.type.add(type);

    return this;
  }

  public LayoutMenuBuilder addTypes(List<Menu.Type> types) {
    if (type == null) {
      type = new ArrayList<>();
    }

    type.addAll(types);

    return this;
  }

  public LayoutMenuBuilder addTypes(Menu.Type... types) {
    return addTypes(Arrays.asList(types));
  }
  
  public LayoutMenuBuilder bukkitType(InventoryType bukkitType) {
    this.bukkitType = bukkitType;

    return this;
  }
  
  public LayoutMenuBuilder openAction(Predicate<InventoryOpenEvent> openAction) {
    this.openAction = openAction;

    return this;
  }

  public LayoutMenuBuilder closeAction(Consumer<InventoryCloseEvent> closeAction) {
    this.closeAction = closeAction;

    return this;
  }

  public LayoutMenuBuilder layout(List<String> layout) {
    if (layout.size() > 6) {
      throw new IllegalArgumentException("The max number of rows is 6.");
    }

    this.layout = layout;

    return this;
  }

  public LayoutMenuBuilder layout(String... layout) {
    if (layout.length > 6) {
      throw new IllegalArgumentException("The max number of rows is 6.");
    }

    this.layout = Arrays.asList(layout);

    return this;
  }

  public LayoutMenuBuilder items(Map<Character, MenuItem> items) {
    this.items = items;

    return this;
  }

  public LayoutMenuBuilder addItem(char key, MenuItem item) {
    if (items == null) {
      items = new HashMap<>();
    }

    items.put(key, item);

    return this;
  }

  public LayoutMenuBuilder blockClicks(boolean blockClicks) {
    this.blockClicks = blockClicks;

    return this;
  }

  @Override
  public LayoutMenu build() {
    checkPreconditions();
    Preconditions.checkNotNull(layout, "null layout");

    return new LayoutMenu(
      title, type, openAction,
      closeAction, layout, items,
      bukkitType, blockClicks
    );
  }
}
