package dev.emmily.daisy.api.item.builder;

import dev.emmily.daisy.api.protocol.nbt.NbtHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemBuilder {
  private Material type;
  private int amount = 1;
  private byte data;
  private String name;
  private List<String> lore;
  private List<ItemFlag> flags;
  private Map<Enchantment, Integer> enchantments;
  private Map<String, Object> tags;

  private ItemBuilder() {
  }

  public static ItemBuilder builder() {
    return new ItemBuilder();
  }

  public ItemBuilder type(Material type) {
    this.type = type;

    return this;
  }

  public ItemBuilder amount(int amount) {
    this.amount = amount;

    return this;
  }


  public ItemBuilder data(byte data) {
    this.data = data;

    return this;
  }

  public ItemBuilder name(String name) {
    this.name = name;

    return this;
  }

  public ItemBuilder lore(List<String> lore) {
    this.lore = lore;

    return this;
  }

  public ItemBuilder addLore(String line) {
    if (lore == null) {
      lore = new ArrayList<>();
    }

    lore.add(line);

    return this;
  }

  public ItemBuilder lore(String... lore) {
    if (this.lore == null) {
      this.lore = new ArrayList<>();
    }

    Collections.addAll(this.lore, lore);

    return this;
  }

  public ItemBuilder flags(List<ItemFlag> flags) {
    this.flags = flags;

    return this;
  }

  public ItemBuilder addFlags(ItemFlag... flags) {
    if (this.flags == null) {
      this.flags = new ArrayList<>();
    }

    this.flags.addAll(Arrays.asList(flags));

    return this;
  }

  public ItemBuilder addFlag(ItemFlag flag) {
    if (flags == null) {
      flags = new ArrayList<>();
    }

    flags.add(flag);

    return this;
  }

  public ItemBuilder enchantments(Map<Enchantment, Integer> enchantments) {
    this.enchantments = enchantments;

    return this;
  }

  public ItemBuilder addEnchantment(Enchantment enchantment,
                                    int level) {
    if (enchantments == null) {
      enchantments = new HashMap<>();
    }

    enchantments.put(enchantment, level);

    return this;
  }

  public ItemBuilder tags(Map<String, Object> tags) {
    this.tags = tags;

    return this;
  }

  public ItemBuilder addTag(String key,
                            Object value) {
    if (tags == null) {
      tags = new HashMap<>();
    }

    tags.put(key, value);

    return this;
  }

  public ItemStack build() {
    ItemStack item = new ItemStack(type, amount, data);
    ItemMeta meta = item.getItemMeta();

    if (name != null) {
      meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
    }

    if (lore != null) {
      lore.replaceAll(s -> ChatColor.translateAlternateColorCodes('&', s));

      meta.setLore(lore);
    }

    if (flags != null) {
      for (ItemFlag flag : flags) {
        meta.addItemFlags(flag);
      }
    }

    if (enchantments != null) {
      for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
        meta.addEnchant(entry.getKey(), entry.getValue(), true);
      }
    }

    item.setItemMeta(meta);

    if (tags != null) {
      for (Map.Entry<String, Object> entry : tags.entrySet()) {
        item = NbtHandler.getInstance().addTag(item, entry.getKey(), entry.getValue());
      }
    }

    return item;
  }
}
