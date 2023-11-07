package dev.emmily.daisy.api.menu.types.dynamic;

import com.google.common.base.Preconditions;
import dev.emmily.daisy.api.item.MenuItem;
import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.menu.types.builder.MenuBuilder;
import org.bukkit.event.inventory.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class DynamicMenuBuilder
  extends MenuBuilder<DynamicMenu> {
  private long updatePeriod;
  private List<List<MenuItem>> frames;
  private List<MenuItem> staticItems;
  private List<Supplier<MenuItem>> suppliableItems;
  private Consumer<DynamicMenu> updateAction;

  public DynamicMenuBuilder title(String title) {
    this.title = title;

    return this;
  }

  public DynamicMenuBuilder size(int size) {
    this.size = size;

    return this;
  }

  public DynamicMenuBuilder items(List<MenuItem> items) {
    this.items = items;

    return this;
  }

  public DynamicMenuBuilder addItem(MenuItem item) {
    if (items == null) {
      items = new ArrayList<>();
    }

    items.add(item);

    return this;
  }

  public DynamicMenuBuilder addItems(List<MenuItem> items) {
    if (this.items == null) {
      this.items = new ArrayList<>();
    }

    this.items.addAll(items);

    return this;
  }

  public DynamicMenuBuilder addItems(MenuItem... items) {
    return addItems(Arrays.asList(items));
  }

  public DynamicMenuBuilder type(List<Menu.Type> type) {
    this.type = type;

    return this;
  }

  public DynamicMenuBuilder addType(Menu.Type type) {
    if (this.type == null) {
      this.type = new ArrayList<>();
    }

    this.type.add(type);

    return this;
  }

  public DynamicMenuBuilder addTypes(List<Menu.Type> types) {
    if (type == null) {
      type = new ArrayList<>();
    }

    type.addAll(types);

    return this;
  }

  public DynamicMenuBuilder addTypes(Menu.Type... types) {
    return addTypes(Arrays.asList(types));
  }

  public DynamicMenuBuilder bukkitType(InventoryType bukkitType) {
    this.bukkitType = bukkitType;

    return this;
  }

  public DynamicMenuBuilder openAction(Predicate<InventoryOpenEvent> openAction) {
    this.openAction = openAction;

    return this;
  }

  public DynamicMenuBuilder closeAction(Consumer<InventoryCloseEvent> closeAction) {
    this.closeAction = closeAction;

    return this;
  }

  public DynamicMenuBuilder updatePeriod(long updatePeriod) {
    this.updatePeriod = updatePeriod;

    return this;
  }

  public DynamicMenuBuilder frames(List<List<MenuItem>> frames) {
    this.frames = frames;

    return this;
  }

  public DynamicMenuBuilder addFrame(List<MenuItem> frame) {
    if (frames == null) {
      frames = new ArrayList<>();
    }

    frames.add(frame);

    return this;
  }

  public DynamicMenuBuilder addFrame(MenuItem... frame) {
    if (frames == null) {
      frames = new ArrayList<>();
    }

    frames.add(Arrays.asList(frame));

    return this;
  }

  public DynamicMenuBuilder suppliableItems(List<Supplier<MenuItem>> items) {
    this.suppliableItems = items;

    return this;
  }

  @SafeVarargs
  public final DynamicMenuBuilder suppliableItems(Supplier<MenuItem>... items) {
    return suppliableItems(Arrays.asList(items));
  }

  public DynamicMenuBuilder addSuppliableItem(Supplier<MenuItem> item) {
    if (suppliableItems == null) {
      suppliableItems = new ArrayList<>();
    }

    suppliableItems.add(item);

    return this;
  }

  public DynamicMenuBuilder addSuppliableItems(List<Supplier<MenuItem>> items) {
    if (suppliableItems == null) {
      suppliableItems = new ArrayList<>();
    }

    suppliableItems.addAll(items);

    return this;
  }

  @SafeVarargs
  public final DynamicMenuBuilder addSuppliableItems(Supplier<MenuItem>... items) {
    return addSuppliableItems(Arrays.asList(items));
  }

  public DynamicMenuBuilder updateAction(Consumer<DynamicMenu> updateAction) {
    this.updateAction = updateAction;

    return this;
  }

  public DynamicMenuBuilder dragAction(Predicate<InventoryDragEvent> dragAction) {
    this.dragAction = dragAction;

    return this;
  }

  public DynamicMenuBuilder unknownSlotClickAction(Predicate<InventoryClickEvent> unknownSlotClickAction) {
    this.unknownSlotClickAction = unknownSlotClickAction;

    return this;
  }

  @Override
  public DynamicMenu build() {
    checkPreconditions();
    Preconditions.checkNotNull(frames, "frames");

    if (staticItems == null) {
      staticItems = Collections.emptyList();
    }

    if (suppliableItems == null) {
      suppliableItems = Collections.emptyList();
    }

    if (updateAction == null) {
      updateAction = menu -> {
      };
    }

    return new DynamicMenu(
      title, size, type,
      openAction, closeAction,
      dragAction, unknownSlotClickAction,
      updatePeriod, frames, staticItems,
      suppliableItems, updateAction, bukkitType
    );
  }
}
