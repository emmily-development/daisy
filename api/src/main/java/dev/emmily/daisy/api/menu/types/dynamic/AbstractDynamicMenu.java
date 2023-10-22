package dev.emmily.daisy.api.menu.types.dynamic;

import dev.emmily.daisy.api.item.MenuItem;
import dev.emmily.daisy.api.menu.Menu;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

// TODO: Replace "static items" with Menu items
public abstract class AbstractDynamicMenu<T>
  extends Menu {
  private final long updatePeriod;
  private long lastUpdate;
  private int currentFrame;
  private final List<List<T>> frames;
  private final List<MenuItem> staticItems;
  private final List<Supplier<MenuItem>> suppliableItems;
  private final InventoryType bukkitType;

  public AbstractDynamicMenu(String title,
                             int size,
                             List<Type> type,
                             Predicate<InventoryOpenEvent> openAction,
                             Consumer<InventoryCloseEvent> closeAction,
                             long updatePeriod,
                             List<List<T>> frames,
                             List<MenuItem> staticItems,
                             List<Supplier<MenuItem>> suppliableItems,
                             InventoryType bukkitType,
                             boolean blockClicks) {
    super(title, size, new ArrayList<>(), type, openAction, closeAction, blockClicks);
    this.updatePeriod = updatePeriod;
    this.frames = frames;
    this.staticItems = staticItems;
    this.suppliableItems = suppliableItems;
    this.bukkitType = bukkitType;
  }

  public long getUpdatePeriod() {
    return updatePeriod;
  }

  public long getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(long lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public List<List<T>> getFrames() {
    return frames;
  }

  public InventoryType getBukkitType() {
    return bukkitType;
  }

  public abstract Consumer<List<T>> getRenderer();

  public void render() {
    if (currentFrame == frames.size()) {
      currentFrame = 0;
    }

    Consumer<List<T>> renderer = getRenderer();

    renderer.accept(frames.get(currentFrame++));

    for (MenuItem staticItem : staticItems) {
      getInventory().setItem(staticItem.getSlot(), staticItem.getItem());
    }

    for (Supplier<MenuItem> suppliableItem : suppliableItems) {
      MenuItem item = suppliableItem.get();

      getInventory().setItem(item.getSlot(), item.getItem());
    }
  }

  public abstract void update();
}
