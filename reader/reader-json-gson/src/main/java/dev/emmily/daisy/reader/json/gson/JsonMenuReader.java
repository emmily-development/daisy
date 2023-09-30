package dev.emmily.daisy.reader.json.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import dev.emmily.daisy.api.item.MenuItem;
import dev.emmily.daisy.api.item.builder.ItemBuilder;
import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.menu.types.builder.MenuBuilder;
import dev.emmily.daisy.api.menu.reader.MenuReader;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class JsonMenuReader
  implements MenuReader {
  private final Gson gson;

  public JsonMenuReader(Gson gson) {
    this.gson = gson;
  }

  public JsonMenuReader() {
    this(new Gson());
  }

  @Override
  public Menu readFromFile(File file) throws IOException {
    return readFromString(new String(Files.readAllBytes(file.toPath())));
  }

  @Override
  public Menu readFromString(String string) throws IOException {
    try (BufferedReader reader = new BufferedReader(new StringReader(string))) {
      JsonObject tree = JsonParser.parseReader(reader).getAsJsonObject();

      switch (tree.get("type").getAsString()) {
        case "chest": {
          return buildDefault(tree, Menu::chestMenuBuilder).build();
        }

        case "updatable": {
          List<List<MenuItem>> frames = new ArrayList<>();

          boolean layout = tree.has("layout");
          Map<String, MenuItem> layoutItems;

          if (layout) {
            layoutItems = new HashMap<>();
          }

          for (JsonElement frame : tree.get("frames").getAsJsonArray()) {
            if (!layout) {
              List<MenuItem> frameItems = new ArrayList<>();
              for (JsonElement frameItem : frame.getAsJsonArray()) {
                frameItems.add(deserializeItem(frameItem));
              }

              frames.add(frameItems);

              return buildDefault(tree, Menu::dynamicMenuBuilder)
                .updatePeriod(tree.get("updatePeriod").getAsLong())
                .frames(frames)
                .build();
            } else {
              List<String> frameItems = new ArrayList<>();

              for (JsonElement frameItem : frame.getAsJsonArray()) {

              }
            }
          }
        }
      }
    }

    return null;
  }

  @SuppressWarnings("unchecked")
  private <T extends MenuBuilder<?>> T buildDefault(JsonObject tree,
                                                    Supplier<T> creator) {
    T builder = (T) creator
      .get()
      .title(tree.get("title").getAsString())
      .size(tree.get("size").getAsInt());

    if (!tree.has("items")) {
      return builder;
    }

    List<MenuItem> items = new ArrayList<>();

    for (JsonElement element : tree.get("items").getAsJsonArray()) {
      items.add(deserializeItem(element.getAsJsonObject()));
    }

    builder = (T) builder.items(items);

    return builder;
  }

  private MenuItem deserializeItem(JsonElement item) {
    JsonObject object = item.getAsJsonObject();

    JsonObject itemObject = object.get("item").getAsJsonObject();
    ItemBuilder itemBuilder = ItemBuilder
      .builder()
      .type(Material.valueOf(itemObject.get("type").getAsString()))
      .amount(itemObject.get("amount").getAsInt())
      .data(itemObject.get("data").getAsByte());

    if (itemObject.has("name")) {
      itemBuilder = itemBuilder.name(itemObject.get("name").getAsString());
    }

    if (itemObject.has("lore")) {
      List<String> lore = gson.fromJson(itemObject.get("lore"), new TypeToken<List<String>>() {
      }.getType());
      itemBuilder = itemBuilder.lore(lore);
    }

    if (itemObject.has("enchantments")) {
      Map<String, Object> rawEnchantments = gson.fromJson(
        itemObject.get("enchantments"),
        new TypeToken<Map<String, Object>>() {
        }.getType()
      );
      Map<Enchantment, Integer> enchantments = new HashMap<>();

      for (Map.Entry<String, Object> enchantment : rawEnchantments.entrySet()) {
        enchantments.put(Enchantment.getByName(enchantment.getKey()), (int) enchantment.getValue());
      }

      itemBuilder = itemBuilder.enchantments(enchantments);
    }

    if (itemObject.has("tags")) {
      itemBuilder = itemBuilder.tags(gson.fromJson(itemObject.get("tags"), new TypeToken<Map<String, Object>>() {
      }.getType()));
    }


    return MenuItem
      .builder()
      .item(itemBuilder.build())
      .slot(object.get("slot").getAsInt())
      .build();
  }
}
