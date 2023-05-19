package dev.emmily.daisy.item;

import com.google.common.base.Preconditions;
import dev.emmily.daisy.action.Action;
import dev.emmily.daisy.protocol.NbtHandler;
import org.bukkit.inventory.ItemStack;

public class MenuItem {
  public static Builder builder() {
    return new Builder();
  }

  private final ItemStack minecraftItem;
  private final Action action;

  private MenuItem(ItemStack minecraftItem,
                   Action action) {
    this.minecraftItem = minecraftItem;
    this.action = action;
  }

  public ItemStack getItem() {
    return minecraftItem;
  }

  public Action getAction() {
    return action;
  }

  public static class Builder {
    private ItemStack minecraftItem;
    private Action action;

    public Builder item(ItemStack minecraftItem) {
      this.minecraftItem = minecraftItem;

      return this;
    }

    public Builder action(Action action) {
      this.action = action;

      return this;
    }

    public MenuItem build(NbtHandler nbtHandler) {
      Preconditions.checkNotNull(minecraftItem, "item cannot be null");
      nbtHandler.addTag(minecraftItem, "daisy", "menu-item");

      return new MenuItem(minecraftItem, action);
    }
  }
}
