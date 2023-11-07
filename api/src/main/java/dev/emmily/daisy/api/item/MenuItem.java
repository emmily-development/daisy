package dev.emmily.daisy.api.item;

import com.google.common.base.Preconditions;
import dev.emmily.daisy.api.protocol.NbtHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.function.Predicate;

public class MenuItem {
  private final Predicate<InventoryClickEvent> action;
  private ItemStack item;
  private int slot;

  private MenuItem(ItemStack item,
                   int slot,
                   Predicate<InventoryClickEvent> action) {
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

  public void setSlot(int slot) {
    this.slot = slot;
  }

  public Predicate<InventoryClickEvent> getAction() {
    return action;
  }

  public MenuItem copy() {
    return new MenuItem(new ItemStack(item), slot, action);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MenuItem menuItem = (MenuItem) o;
    return slot == menuItem.slot && Objects.equals(item, menuItem.item);
  }

  @Override
  public int hashCode() {
    return Objects.hash(item, slot);
  }

  public static class Builder {
    private ItemStack minecraftItem;
    private int slot;
    private Predicate<InventoryClickEvent> action;

    public Builder item(ItemStack minecraftItem) {
      this.minecraftItem = minecraftItem;

      return this;
    }

    public Builder slot(int slot) {
      this.slot = slot;

      return this;
    }

    public Builder action(Predicate<InventoryClickEvent> action) {
      this.action = action;

      return this;
    }

    public MenuItem build() {
      Preconditions.checkNotNull(minecraftItem, "item cannot be null");
      minecraftItem = NbtHandler.getInstance().addTag(minecraftItem, "daisy", "menu-item");

      return new MenuItem(minecraftItem, slot, action);
    }
  }
}
