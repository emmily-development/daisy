package dev.emmily.daisy.menu.types.updatable;

import dev.emmily.daisy.item.MenuItem;
import dev.emmily.daisy.menu.Menu;
import org.bukkit.inventory.Inventory;

import java.beans.ConstructorProperties;
import java.util.List;

public class UpdatableMenu {
  private final Menu delegate;
  private final long updatePeriod;
  private long lastUpdate;
  private final List<List<MenuItem>> frames;
  private int currentFrame;

  @ConstructorProperties({
    "delegate", "updatePeriod",
    "frames"
  })
  public UpdatableMenu(Menu delegate,
                       long updatePeriod,
                       List<List<MenuItem>> frames) {
    this.delegate = delegate;
    this.updatePeriod = updatePeriod;
    this.lastUpdate = System.currentTimeMillis();
    this.frames = frames;
  }

  public Menu getDelegate() {
    return delegate;
  }

  public long getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(long lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public long getUpdatePeriod() {
    return updatePeriod;
  }

  public List<List<MenuItem>> getFrames() {
    return frames;
  }

  public void update() {
    Inventory inventory = delegate.getInventory();
    inventory.clear();

    if (currentFrame == frames.size()) {
      currentFrame = 0;
    }

    delegate.getItems().clear();
    delegate.getItems().addAll(frames.get(currentFrame++));

    for (MenuItem item : delegate.getItems()) {
      if (item == null) {
        continue;
      }

      inventory.setItem(item.getSlot(), item.getItem());
    }
  }
}
