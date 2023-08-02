package dev.emmily.daisy.menu.scheduler;

import dev.emmily.daisy.menu.types.updatable.UpdatableMenu;
import dev.emmily.daisy.menu.types.updatable.UpdatableMenuRegistry;

public class UpdatableMenuScheduler
  implements Runnable {
  private final UpdatableMenuRegistry registry;

  public UpdatableMenuScheduler(UpdatableMenuRegistry registry) {
    this.registry = registry;
  }

  @Override
  public void run() {
    for (UpdatableMenu updatableMenu : registry.getAll()) {
      if ((System.currentTimeMillis() - updatableMenu.getLastUpdate()) >= updatableMenu.getUpdatePeriod()) {
        updatableMenu.update();
      }
    }
  }
}
