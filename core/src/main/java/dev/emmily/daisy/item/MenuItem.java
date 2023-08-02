package dev.emmily.daisy.item;

import com.google.common.base.Preconditions;
import dev.emmily.daisy.action.Action;
import dev.emmily.daisy.protocol.NbtHandler;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MenuItem {
  private ItemStack item;
  private final int slot;
  private final Action action;

  private MenuItem(ItemStack item,
                   int slot,
                   Action action) {
    this.item = item;
    this.slot = slot;
    this.action = action;
  }

  public static Builder builder() {
    return new Builder();
  }

  public ItemStack getItem() {
    return item;
  }

  public void setItem(ItemStack item) {
    this.item = item;
  }

  public int getSlot() {
    return slot;
  }

  public Action getAction() {
    return action;
  }

  public static class Builder {
    private ItemStack minecraftItem;
    private int slot;
    private Action action;

    public Builder item(ItemStack minecraftItem) {
      this.minecraftItem = minecraftItem;

      return this;
    }

    public Builder slot(int slot) {
      this.slot = slot;

      return this;
    }

    public Builder action(Action action) {
      this.action = action;

      return this;
    }

    public MenuItem build(NbtHandler nbtHandler) {
      Preconditions.checkNotNull(minecraftItem, "item cannot be null");
      nbtHandler.addTag(minecraftItem, "daisy", "menu-item");

      return new MenuItem(minecraftItem, slot, action);
    }
  }
}
