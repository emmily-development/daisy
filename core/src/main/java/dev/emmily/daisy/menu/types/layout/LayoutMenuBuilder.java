package dev.emmily.daisy.menu.types.layout;

import com.google.common.base.Preconditions;
import dev.emmily.daisy.item.MenuItem;
import dev.emmily.daisy.menu.Menu;
import dev.emmily.daisy.menu.types.builder.MenuBuilder;
import dev.emmily.daisy.menu.types.chest.ChestSize;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

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
      super.items = new ArrayList<>(Collections.nCopies(ChestSize.toSlots(6), null));
    }

    super.items.set(item.getSlot(), item);

    return this;
  }

  public LayoutMenuBuilder addItems(MenuItem... items) {
    if (super.items == null) {
      super.items = new ArrayList<>(Collections.nCopies(ChestSize.toSlots(6), null));
    }

    for (MenuItem item : items) {
      super.items.add(item.getSlot(), item);
    }

    return this;
  }

  public LayoutMenuBuilder type(Menu.Type type) {
    this.type = type;

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
    if (layout.size() == 6) {
      throw new IllegalArgumentException("The max number of rows is 6.");
    }

    this.layout = layout;

    return this;
  }

  public LayoutMenuBuilder layout(String... layout) {
    if (layout.length > 5) {
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
      Preconditions.checkArgument(layout != null, "you must define the layout before adding items");

      items = new HashMap<>(ChestSize.toSlots(layout.size()));
    }

    items.put(key, item);

    return this;
  }

  @Override
  public LayoutMenu build() {
    Preconditions.checkNotNull(layout, "null layout");
    Preconditions.checkArgument(layout.size() <= 6, "the maximum number of rows for a layout is 6");

    LayoutMenu menu = new LayoutMenu(
      title, openAction,
      closeAction,
      layout, items
    );

    int index = 0;
    for (String line : layout) {
      Preconditions.checkArgument(line.length() <= 9, "the maximum size for a line is 9");

      for (char key : line.toCharArray()) {
        MenuItem item = items.get(key);

        if (item == null) {
          index++;

          continue;
        }

        menu.getItems().set(index, item);
        menu.getInventory().setItem(index, item.getItem());

        index++;
      }
    }

    return menu;
  }
}
