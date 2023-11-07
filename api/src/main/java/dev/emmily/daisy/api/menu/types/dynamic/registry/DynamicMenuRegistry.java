package dev.emmily.daisy.api.menu.types.dynamic.registry;

import dev.emmily.daisy.api.menu.types.dynamic.AbstractDynamicMenu;

import java.util.ArrayList;
import java.util.List;

public class DynamicMenuRegistry {
  private final List<AbstractDynamicMenu<?>> dynamicMenus;

  public DynamicMenuRegistry() {
    this.dynamicMenus = new ArrayList<>();
  }

  public void register(AbstractDynamicMenu<?> menu) {
    dynamicMenus.add(menu);
  }

  public void unregister(AbstractDynamicMenu<?> menu) {
    dynamicMenus.remove(menu);
  }

  public void clear() {
    dynamicMenus.clear();
  }

  public List<AbstractDynamicMenu<?>> getAll() {
    return dynamicMenus;
  }
}
