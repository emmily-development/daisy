package dev.emmily.daisy.menu.types.layout;

import com.google.common.base.Preconditions;
import dev.emmily.daisy.action.Action;
import dev.emmily.daisy.item.MenuItem;
import dev.emmily.daisy.menu.Menu;
import dev.emmily.daisy.menu.types.chest.ChestSize;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class LayoutMenu
  extends Menu {
  private final List<String> layout;
  private final Map<Character, MenuItem> items;
  private final Inventory inventory;

  @ConstructorProperties({
    "delegate",
    "layout",
    "items"
  })
  public LayoutMenu(String title,
                    Action openAction,
                    Action closeAction,
                    List<String> layout,
                    Map<Character, MenuItem> items) {
    super(
      title, ChestSize.toSlots(layout.size()),
      new ArrayList<>(Collections.nCopies(ChestSize.toSlots(6), null)), Type.LAYOUT,
      openAction, closeAction
    );
    this.layout = layout;
    this.items = items;
    this.inventory = Bukkit.createInventory(this, getActualSize(), title);
  }

  public List<String> getLayout() {
    return layout;
  }

  public Map<Character, MenuItem> getLayoutItems() {
    return items;
  }

  @Override
  public Inventory getInventory() {
    return inventory;
  }
}
