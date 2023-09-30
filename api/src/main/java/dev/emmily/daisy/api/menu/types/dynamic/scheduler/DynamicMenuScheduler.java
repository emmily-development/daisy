package dev.emmily.daisy.api.menu.types.dynamic.scheduler;

import dev.emmily.daisy.api.menu.types.dynamic.AbstractDynamicMenu;
import dev.emmily.daisy.api.menu.types.dynamic.registry.DynamicMenuRegistry;

public class DynamicMenuScheduler
  implements Runnable {
  private final DynamicMenuRegistry registry;

  public DynamicMenuScheduler(DynamicMenuRegistry registry) {
    this.registry = registry;
  }

  @Override
  public void run() {
    for (AbstractDynamicMenu<?> menu : registry.getAll()) {
      if ((System.currentTimeMillis() - menu.getLastUpdate()) >= menu.getUpdatePeriod()) {
        menu.render();
        menu.update();
      }
    }
  }
}
