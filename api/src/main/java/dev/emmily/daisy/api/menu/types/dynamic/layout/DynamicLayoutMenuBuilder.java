package dev.emmily.daisy.api.menu.types.dynamic.layout;

import com.google.common.base.Preconditions;
import dev.emmily.daisy.api.item.MenuItem;
import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.menu.types.builder.MenuBuilder;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class DynamicLayoutMenuBuilder 
  extends MenuBuilder<DynamicLayoutMenu> {
  private long updatePeriod;
  private List<List<String>> frames;
  private Map<Character, MenuItem> itemsByKey;
  private List<MenuItem> staticItems;
  private List<Supplier<MenuItem>> suppliableItems;
  private Consumer<DynamicLayoutMenu> updateAction;

  public DynamicLayoutMenuBuilder title(String title) {
    this.title = title;

    return this;
  }

  public DynamicLayoutMenuBuilder size(int size) {
    this.size = size;

    return this;
  }

  public DynamicLayoutMenuBuilder items(List<MenuItem> items) {
    this.items = items;

    return this;
  }

  public DynamicLayoutMenuBuilder addItem(MenuItem item) {
    if (items == null) {
      items = new ArrayList<>();
    }

    items.add(item);

    return this;
  }

  public DynamicLayoutMenuBuilder addItems(List<MenuItem> items) {
    if (this.items == null) {
      this.items = new ArrayList<>();
    }

    this.items.addAll(items);

    return this;
  }

  public DynamicLayoutMenuBuilder addItems(MenuItem... items) {
    return addItems(Arrays.asList(items));
  }

  public DynamicLayoutMenuBuilder type(List<Menu.Type> type) {
    this.type = type;

    return this;
  }

  public DynamicLayoutMenuBuilder addType(Menu.Type type) {
    if (this.type == null) {
      this.type = new ArrayList<>();
    }

    this.type.add(type);

    return this;
  }

  public DynamicLayoutMenuBuilder addTypes(List<Menu.Type> types) {
    if (type == null) {
      type = new ArrayList<>();
    }

    type.addAll(types);

    return this;
  }

  public DynamicLayoutMenuBuilder addTypes(Menu.Type... types) {
    return addTypes(Arrays.asList(types));
  }

  public DynamicLayoutMenuBuilder bukkitType(InventoryType bukkitType) {
    this.bukkitType = bukkitType;

    return this;
  }
  
  public DynamicLayoutMenuBuilder openAction(Predicate<InventoryOpenEvent> openAction) {
    this.openAction = openAction;

    return this;
  }

  public DynamicLayoutMenuBuilder closeAction(Consumer<InventoryCloseEvent> closeAction) {
    this.closeAction = closeAction;

    return this;
  }

  public DynamicLayoutMenuBuilder updatePeriod(long updatePeriod) {
    this.updatePeriod = updatePeriod;

    return this;
  }

  public DynamicLayoutMenuBuilder frames(List<List<String>> frames) {
    this.frames = frames;

    return this;
  }

  public DynamicLayoutMenuBuilder addFrame(List<String> frame) {
    if (frames == null) {
      frames = new ArrayList<>();
    }

    frames.add(frame);

    return this;
  }

  public DynamicLayoutMenuBuilder addFrame(String... frames) {
    return addFrame(Arrays.asList(frames));
  }

  public DynamicLayoutMenuBuilder itemsByKey(Map<Character, MenuItem> itemsByKey) {
    this.itemsByKey = itemsByKey;

    return this;
  }

  public DynamicLayoutMenuBuilder addItem(char key,
                                          MenuItem item) {
    if (itemsByKey == null) {
      itemsByKey = new HashMap<>();
    }

    itemsByKey.put(key, item);

    return this;
  }

  public DynamicLayoutMenuBuilder staticItems(List<MenuItem> items) {
    this.staticItems = items;

    return this;
  }

  public DynamicLayoutMenuBuilder staticItems(MenuItem... items) {
    return staticItems(Arrays.asList(items));
  }

  public DynamicLayoutMenuBuilder addStaticItem(MenuItem item) {
    if (staticItems == null) {
      staticItems = new ArrayList<>();
    }

    staticItems.add(item);

    return this;
  }

  public DynamicLayoutMenuBuilder addStaticItems(List<MenuItem> items) {
    if (staticItems == null){
      staticItems = new ArrayList<>();
    }

    staticItems.addAll(items);

    return this;
  }

  public DynamicLayoutMenuBuilder addStaticItems(MenuItem... items) {
    return addStaticItems(Arrays.asList(items));
  }

  public DynamicLayoutMenuBuilder suppliableItems(List<Supplier<MenuItem>> items) {
    this.suppliableItems = items;

    return this;
  }

  @SafeVarargs
  public final DynamicLayoutMenuBuilder suppliableItems(Supplier<MenuItem>... items) {
    return suppliableItems(Arrays.asList(items));
  }

  public DynamicLayoutMenuBuilder addSuppliableItem(Supplier<MenuItem> item) {
    if (suppliableItems == null) {
      suppliableItems = new ArrayList<>();
    }

    suppliableItems.add(item);

    return this;
  }

  public DynamicLayoutMenuBuilder addSuppliableItems(List<Supplier<MenuItem>> items) {
    if (suppliableItems == null){
      suppliableItems = new ArrayList<>();
    }

    suppliableItems.addAll(items);

    return this;
  }

  @SafeVarargs
  public final DynamicLayoutMenuBuilder addSuppliableItems(Supplier<MenuItem>... items) {
    return addSuppliableItems(Arrays.asList(items));
  }

  public DynamicLayoutMenuBuilder updateAction(Consumer<DynamicLayoutMenu> updateAction) {
    this.updateAction = updateAction;

    return this;
  }

  public DynamicLayoutMenuBuilder blockClicks(boolean blockClicks) {
    this.blockClicks = blockClicks;

    return this;
  }

  @Override
  public DynamicLayoutMenu build() {
    checkPreconditions();
    Preconditions.checkNotNull(frames, "frames");
    Preconditions.checkNotNull(itemsByKey, "items");

    if (staticItems == null) {
      staticItems = Collections.emptyList();
    }

    if (suppliableItems == null) {
      suppliableItems = Collections.emptyList();
    }

    if (updateAction == null) {
      updateAction = menu -> {};
    }

    return new DynamicLayoutMenu(
      title, size, type, openAction,
      closeAction, updatePeriod, frames,
      staticItems, suppliableItems,
      updateAction, itemsByKey, bukkitType,
      blockClicks
    );
  }
}
