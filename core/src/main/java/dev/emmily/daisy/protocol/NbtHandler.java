package dev.emmily.daisy.protocol;

import org.bukkit.inventory.ItemStack;

public interface NbtHandler {
  /**
   * Adds (or replaces if existing) an
   * NBT entry with the given {@code id}
   * and the given {@code value} to the
   * given {@code item}'s NBT tag.
   *
   * @param item  The item to add the tag to.
   * @param key   The key of the tag.
   * @param value The value of the tag.
   * @return The given {@code item} with the
   * applied NBT tag.
   */
  ItemStack addTag(ItemStack item,
                   String key,
                   String value);

  /**
   * Returns {@code true} if the
   * given {@code item} has an NBT
   * entry with the given {@code id}
   * in its NBT tag.
   *
   * @param item The item to be checked.
   * @param key  The key to be checked.
   * @return {@code true} if the
   * given {@code item} has an
   * entry with the given {@code id}
   * in its NBT tag.
   */
  boolean hasTag(ItemStack item,
                 String key);
}
