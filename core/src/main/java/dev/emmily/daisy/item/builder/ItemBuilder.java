package dev.emmily.daisy.item.builder;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ItemBuilder {
  private Material type;
  private int amount;
  private byte data;
  private String name;
  private List<String> lore;
  private Map<String, String> tags;

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

    item.setItemMeta(meta);

    return item;
  }
}
