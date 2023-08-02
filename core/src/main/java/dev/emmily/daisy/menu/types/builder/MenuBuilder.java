package dev.emmily.daisy.menu.types.builder;

import dev.emmily.daisy.action.Action;
import dev.emmily.daisy.item.MenuItem;
import dev.emmily.daisy.menu.Menu;

import java.util.ArrayList;
import java.util.List;

public abstract class MenuBuilder<T extends Menu> {
  protected String title;
  protected int size;
  protected List<MenuItem> items;
  protected Menu.Type type;
  protected Action openAction;
  protected Action closeAction;

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

    items.set(item.getSlot(), item);

    return this;
  }

  public MenuBuilder<T> addItems(MenuItem... items) {
    for (MenuItem item : items) {
      this.items.set(item.getSlot(), item);
    }

    return this;
  }

  public MenuBuilder<T> type(Menu.Type type) {
    this.type = type;

    return this;
  }

  public MenuBuilder<T> openAction(Action openAction) {
    this.openAction = openAction;

    return this;
  }

  public MenuBuilder<T> closeAction(Action closeAction) {
    this.closeAction = closeAction;

    return this;
  }

  public abstract T build();
}
