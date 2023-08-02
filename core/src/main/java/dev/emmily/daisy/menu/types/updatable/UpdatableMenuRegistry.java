package dev.emmily.daisy.menu.types.updatable;

import java.util.ArrayList;
import java.util.List;

public class UpdatableMenuRegistry {
  private final List<UpdatableMenu> updatableMenus;

  public UpdatableMenuRegistry() {
    this.updatableMenus = new ArrayList<>();
  }

  public void register(UpdatableMenu menu) {
    updatableMenus.add(menu);
  }

  public void unregister(UpdatableMenu menu) {
    updatableMenus.remove(menu);
  }

  public void clear() {
    updatableMenus.clear();
  }

  public List<UpdatableMenu> getAll() {
    return updatableMenus;
  }
}
