package dev.emmily.daisy.api.menu.types.paginated;

import dev.emmily.daisy.api.item.MenuItem;
import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.protocol.NbtHandler;
import dev.emmily.daisy.api.util.TriConsumer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * An extension of {@link Menu} that allows
 * the developer to render several items when
 * those can't be rendered in a single inventory
 * separating them into pages. Each page has a
 * maximum amount of elements and some slots
 * which will be empty.
 *
 * @param <T> The type of elements that this
 *            paginated menu can render.
 */
public class PaginatedMenu<T>
  extends Menu {
  private final Inventory inventory;
  private final List<List<T>> pages;
  private final BiFunction<T, Integer, MenuItem> elementParser;
  private final List<Integer> skippedSlots;
  private final MenuItem previousPageSwitch;
  private final MenuItem nextPageSwitch;
  private final TriConsumer<Integer, Integer, PageOperand> pageSwitchAction;
  private final List<MenuItem> pageItems;
  private int currentPage;

  /**
   * Creates a paginated menu with elements of
   * type {@link T}.
   *
   * @param title              The title of the menu.
   * @param size               The size of the menu.
   * @param items              The default items of the menu
   * @param type               The types of the menu.
   * @param openAction         The action executed when the
   *                           inventory is opened.
   * @param closeAction        The action executed when the
   *                           inventory is closed.
   * @param dragAction         The action executed when
   *                           items are dragged to the
   *                           menu.
   * @param elements           The elements to be paginated.
   * @param elementParser      The function in charge of
   *                           converting the provided
   *                           elements into {@link MenuItem}s.
   * @param elementsPerPage    The amount of elements that
   *                           every page must contain.
   * @param skippedSlots       The slots that won't hold an element.
   * @param previousPageSwitch The item that lets the player go back
   *                           to the previous page.
   * @param nextPageSwitch     The item that lets the player advance to
   *                           the next page.
   * @param pageSwitchAction   The action executed when the page is switched.
   * @param bukkitType         The bukkit inventory type.
   */
  public PaginatedMenu(String title,
                       int size,
                       List<MenuItem> items,
                       List<Type> type,
                       Predicate<InventoryOpenEvent> openAction,
                       Consumer<InventoryCloseEvent> closeAction,
                       Predicate<InventoryDragEvent> dragAction,
                       Predicate<InventoryClickEvent> unknownSlotClickAction,
                       List<T> elements,
                       BiFunction<T, Integer, MenuItem> elementParser,
                       int elementsPerPage,
                       List<Integer> skippedSlots,
                       MenuItem previousPageSwitch,
                       MenuItem nextPageSwitch,
                       TriConsumer<Integer, Integer, PageOperand> pageSwitchAction,
                       InventoryType bukkitType) {
    super(
      title, size, items,
      type, openAction,
      closeAction, dragAction,
      unknownSlotClickAction
    );
    this.pageSwitchAction = pageSwitchAction;
    this.inventory = bukkitType ==
      InventoryType.CHEST || bukkitType == InventoryType.ENDER_CHEST
      ? Bukkit.createInventory(this, size, title)
      : Bukkit.createInventory(this, bukkitType, title);
    if (elementsPerPage < 0) {
      throw new IllegalArgumentException("elements per page must be at least at 1");
    }

    this.pages = new ArrayList<>();

    int totalElements = elements.size();
    int startIndex = 0;

    while (startIndex < totalElements) {
      int endIndex = Math.min(startIndex + elementsPerPage, totalElements);
      pages.add(elements.subList(startIndex, endIndex));

      startIndex = endIndex;
    }

    this.elementParser = elementParser;
    this.skippedSlots = skippedSlots;
    this.previousPageSwitch = previousPageSwitch;
    this.previousPageSwitch.setItem(NbtHandler.getInstance().addTag(
      previousPageSwitch.getItem(),
      "daisy-page-switch",
      "previous"
    ));
    this.nextPageSwitch = nextPageSwitch;
    this.nextPageSwitch.setItem(NbtHandler.getInstance().addTag(
      nextPageSwitch.getItem(),
      "daisy-page-switch",
      "next"
    ));
    this.skippedSlots.add(previousPageSwitch.getSlot());
    this.skippedSlots.add(nextPageSwitch.getSlot());
    this.pageItems = new ArrayList<>();
  }

  /**
   * Retrieves the list of elements contained
   * in the current page ({@link #currentPage}).
   *
   * @return The list of elements contained
   * in the current page ({@link #currentPage}).
   */
  public List<T> getCurrentPageElements() {
    if (currentPage >= 0 && currentPage < pages.size()) {
      return pages.get(currentPage);
    }

    return new ArrayList<>();
  }

  public boolean hasNextPage() {
    return currentPage < pages.size() - 1;
  }

  /**
   * Advances to the next page, if possible.
   */
  public void nextPage() {
    if (hasNextPage()) {
      currentPage++;
    }
  }

  public boolean hasPreviousPage() {
    return currentPage > 0;
  }

  /**
   * Goes back to the previous page, if possible.
   */
  public void previousPage() {
    if (hasPreviousPage()) {
      currentPage--;
    }
  }

  /**
   * Renders the elements provided by {@link #getCurrentPageElements()}
   * in the {@link #inventory}.
   *
   * @param operand Indicates whether the page should
   *                go forward, backward, or stay.
   */
  public void render(PageOperand operand) {
    inventory.clear();

    for (MenuItem item : getItems()) {
      inventory.setItem(item.getSlot(), item.getItem());
    }

    if (operand == PageOperand.NEXT) {
      nextPage();
    } else if (operand == PageOperand.PREVIOUS) {
      previousPage();
    }

    if (hasPreviousPage()) {
      inventory.setItem(previousPageSwitch.getSlot(), previousPageSwitch.getItem());
    }

    if (hasNextPage()) {
      inventory.setItem(nextPageSwitch.getSlot(), nextPageSwitch.getItem());
    }

    populateCopy();

    List<T> elements = getCurrentPageElements();

    int slot = 0;

    for (T element : elements) {
      while (skippedSlots.contains(slot)) {
        slot++;
      }

      int elementIndex = getElementIndex(element);
      MenuItem item = elementParser.apply(element, elementIndex);

      if (item.getSlot() == 0 && elementIndex != 0) {
        pageItems.add(item);
        getItems().add(item);
        inventory.setItem(slot++, item.getItem());
      } else {
        slot++;
        pageItems.add(item);
        getItems().add(item);
        inventory.setItem(item.getSlot(), item.getItem());
      }
    }
  }

  private void populateCopy() {
    for (MenuItem copy : pageItems) {
      getItems().remove(copy);
    }

    pageItems.clear();

    pageItems.add(previousPageSwitch);
    getItems().add(previousPageSwitch);
    pageItems.add(nextPageSwitch);
    getItems().add(nextPageSwitch);
  }

  /**
   * Returns the index of the {@code element}
   * in the {@link #pages} list, treating it
   * as a non-paged list.
   *
   * @param element The element to get the index from.
   * @return The index of the {@code element}
   * in the {@link #pages} list, treating it
   * as a non-paged list.
   */
  private int getElementIndex(T element) {
    int globalIndex = 0;

    for (List<T> page : pages) {
      int index = page.indexOf(element);

      if (index != -1) {
        return globalIndex + index;
      }

      globalIndex += page.size();
    }

    return -1;
  }

  /**
   * Returns the previous page item
   * switch.
   *
   * @return The previous page item
   * switch.
   */
  public MenuItem getPreviousPageSwitch() {
    return previousPageSwitch;
  }

  /**
   * Returns the next page item
   * switch.
   *
   * @return The next page item
   * switch.
   */
  public MenuItem getNextPageSwitch() {
    return nextPageSwitch;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  @Override
  public Inventory getInventory() {
    return inventory;
  }

  public TriConsumer<Integer, Integer, PageOperand> getPageSwitchAction() {
    return pageSwitchAction;
  }

  @Override
  public void open(Player player) {
    render(PageOperand.CURRENT);
    super.open(player);
  }

  public enum PageOperand {
    NEXT,
    PREVIOUS,
    CURRENT
  }
}
