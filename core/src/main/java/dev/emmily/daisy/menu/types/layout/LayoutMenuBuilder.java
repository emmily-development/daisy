package dev.emmily.daisy.menu.types.layout;

import com.google.common.base.Preconditions;
import dev.emmily.daisy.item.MenuItem;
import dev.emmily.daisy.menu.types.builder.MenuBuilder;
import dev.emmily.daisy.menu.types.chest.ChestSize;

import java.util.*;

public class LayoutMenuBuilder
  extends MenuBuilder<LayoutMenu> {
  private List<String> layout;
  private Map<Character, MenuItem> items;

  public LayoutMenuBuilder layout(List<String> layout) {
    this.layout = layout;

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
