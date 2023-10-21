package dev.emmily.daisy.api.menu.types.paginated;

import com.google.common.base.Preconditions;
import dev.emmily.daisy.api.item.MenuItem;
import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.menu.types.builder.MenuBuilder;
import dev.emmily.daisy.api.util.TriConsumer;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PaginatedMenuBuilder<T>
  extends MenuBuilder<PaginatedMenu<T>> {
  private List<T> elements;
  private BiFunction<T, Integer, MenuItem> elementParser;
  private int elementsPerPage;
  private List<Integer> skippedSlots;
  private MenuItem previousPageSwitch;
  private MenuItem nextPageSwitch;
  private TriConsumer<Integer, Integer, PaginatedMenu.PageOperand> pageSwitchAction;

  public PaginatedMenuBuilder<T> title(String title) {
    this.title = title;

    return this;
  }

  public PaginatedMenuBuilder<T> size(int size) {
    this.size = size;

    return this;
  }

  public PaginatedMenuBuilder<T> items(List<MenuItem> items) {
    this.items = items;

    return this;
  }

  public PaginatedMenuBuilder<T> addItem(MenuItem item) {
    if (items == null) {
      items = new ArrayList<>();
    }

    items.add(item);

    return this;
  }

  public PaginatedMenuBuilder<T> addItems(List<MenuItem> items) {
    if (this.items == null) {
      this.items = new ArrayList<>();
    }

    this.items.addAll(items);

    return this;
  }

  public PaginatedMenuBuilder<T> addItems(MenuItem... items) {
    return addItems(Arrays.asList(items));
  }

  public PaginatedMenuBuilder<T> type(List<Menu.Type> type) {
    this.type = type;

    return this;
  }

  public PaginatedMenuBuilder<T> addType(Menu.Type type) {
    if (this.type == null) {
      this.type = new ArrayList<>();
    }

    this.type.add(type);

    return this;
  }

  public PaginatedMenuBuilder<T> addTypes(List<Menu.Type> types) {
    if (type == null) {
      type = new ArrayList<>();
    }

    type.addAll(types);

    return this;
  }

  public PaginatedMenuBuilder<T> addTypes(Menu.Type... types) {
    return addTypes(Arrays.asList(types));
  }

  public PaginatedMenuBuilder<T> bukkitType(InventoryType bukkitType) {
    this.bukkitType = bukkitType;

    return this;
  }

  public PaginatedMenuBuilder<T> openAction(Predicate<InventoryOpenEvent> openAction) {
    this.openAction = openAction;

    return this;
  }

  public PaginatedMenuBuilder<T> closeAction(Consumer<InventoryCloseEvent> closeAction) {
    this.closeAction = closeAction;

    return this;
  }

  public PaginatedMenuBuilder<T> elements(List<T> elements) {
    this.elements = elements;

    return this;
  }

  public PaginatedMenuBuilder<T> addElement(T element) {
    if (elements == null) {
      elements = new ArrayList<>();
    }

    elements.add(element);

    return this;
  }

  public PaginatedMenuBuilder<T> addElements(List<T> elements) {
    if (this.elements == null) {
      this.elements = new ArrayList<>();
    }

    this.elements.addAll(elements);

    return this;
  }

  @SafeVarargs
  public final PaginatedMenuBuilder<T> addElements(T... elements) {
    return addElements(Arrays.asList(elements));
  }

  public PaginatedMenuBuilder<T> elementParser(BiFunction<T, Integer, MenuItem> elementParser) {
    this.elementParser = elementParser;

    return this;
  }

  public PaginatedMenuBuilder<T> elementsPerPage(int elementsPerPage) {
    this.elementsPerPage = elementsPerPage;

    return this;
  }

  public PaginatedMenuBuilder<T> skippedSlots(List<Integer> skippedSlots) {
    this.skippedSlots = skippedSlots;

    return this;
  }

  public PaginatedMenuBuilder<T> addSkippedSlot(int skippedSlot) {
    if (skippedSlots == null) {
      skippedSlots = new ArrayList<>();
    }

    skippedSlots.add(skippedSlot);

    return this;
  }

  public PaginatedMenuBuilder<T> addSkippedSlots(List<Integer> skippedSlots) {
    if (this.skippedSlots == null) {
      this.skippedSlots = new ArrayList<>();
    }

    this.skippedSlots.addAll(skippedSlots);

    return this;
  }

  public PaginatedMenuBuilder<T> addSkippedSlots(int... skippedSlots) {
    if (this.skippedSlots == null) {
      this.skippedSlots = new ArrayList<>();
    }

    for (int skippedSlot : skippedSlots) {
      this.skippedSlots.add(skippedSlot);
    }

    return this;
  }

  public PaginatedMenuBuilder<T> previousPageSwitch(MenuItem previousPageSwitch) {
    this.previousPageSwitch = previousPageSwitch;

    return this;
  }

  public PaginatedMenuBuilder<T> nextPageSwitch(MenuItem nextPageSwitch) {
    this.nextPageSwitch = nextPageSwitch;

    return this;
  }

  public PaginatedMenuBuilder<T> pageSwitchAction(TriConsumer<Integer, Integer, PaginatedMenu.PageOperand> pageSwitchAction) {
    this.pageSwitchAction = pageSwitchAction;

    return this;
  }

  @Override
  public PaginatedMenu<T> build() {
    checkPreconditions();
    Preconditions.checkNotNull(previousPageSwitch, "previous page switch");
    Preconditions.checkNotNull(nextPageSwitch, "next page switch");

    if (skippedSlots == null) {
      skippedSlots = new ArrayList<>();
    }

    return new PaginatedMenu<>(
      title, size, items, type,
      openAction, closeAction,
      elements, elementParser,
      elementsPerPage, skippedSlots,
      previousPageSwitch, nextPageSwitch,
      pageSwitchAction, bukkitType
    );
  }
}
