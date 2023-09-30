package dev.emmily.daisy.reader.json.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.emmily.daisy.api.item.MenuItem;
import dev.emmily.daisy.api.item.builder.ItemBuilder;
import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.menu.types.builder.MenuBuilder;
import dev.emmily.daisy.api.menu.reader.MenuReader;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class JsonMenuReader
  implements MenuReader {
  private final ObjectMapper objectMapper;

  public JsonMenuReader(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public JsonMenuReader() {
    this(new ObjectMapper());
  }

  @Override
  public Menu readFromFile(File file) throws IOException {
    return readFromString(new String(Files.readAllBytes(Paths.get(file.toURI()))));
  }

  @Override
  public Menu readFromString(String string) throws IOException {
    try (BufferedReader reader = new BufferedReader(new StringReader(string))) {
      JsonNode tree = objectMapper.readTree(reader);

      switch (tree.get("type").asText()) {
        case "chest": {
          return buildDefault(tree, Menu::chestMenuBuilder).build();
        }

        case "updatable": {
          List<List<MenuItem>> frames = new ArrayList<>();

          for (JsonNode frame : tree.get("frames")) {
            List<MenuItem> frameItems = new ArrayList<>();

            for (JsonNode frameItem : frame) {
              frameItems.add(deserializeItem(frameItem));
            }

            frames.add(frameItems);
          }

          return buildDefault(tree, Menu::dynamicMenuBuilder)
            .updatePeriod(tree.get("updatePeriod").asLong())
            .frames(frames)
            .build();
        }
      }
    }

    return null;
  }

  @SuppressWarnings("unchecked")
  private <T extends MenuBuilder<?>> T buildDefault(JsonNode tree,
                                                    Supplier<T> creator) {
    T builder = (T) creator
      .get()
      .title(tree.get("title").asText())
      .size(tree.get("size").asInt());

    if (!tree.has("items")) {
      return builder;
    }

    List<MenuItem> items = new ArrayList<>();

    for (JsonNode itemNode : tree.get("items")) {
      items.add(deserializeItem(itemNode));
    }

    builder = (T) builder.items(items);

    return builder;
  }

  @SuppressWarnings("unchecked")
  private MenuItem deserializeItem(JsonNode item) {
    JsonNode itemNode = item.get("item");
    ItemBuilder itemBuilder = ItemBuilder
      .builder()
      .type(Material.valueOf(itemNode.get("type").asText()))
      .amount(itemNode.get("amount").asInt())
      .data(itemNode.get("data").numberValue().byteValue());

    if (itemNode.has("name")) {
      itemBuilder = itemBuilder.name(itemNode.get("name").asText());
    }

    if (itemNode.has("lore")) {
      List<String> lore = new ArrayList<>();

      for (JsonNode loreNode : itemNode.get("lore")) {
        lore.add(loreNode.asText());
      }

      itemBuilder = itemBuilder.lore(lore);
    }

    if (itemNode.has("enchantments")) {
      Map<String, Object> rawEnchantments = objectMapper.convertValue(itemNode.get("enchantments"), Map.class);
      Map<Enchantment, Integer> enchantments = new HashMap<>();

      for (Map.Entry<String, Object> entry : rawEnchantments.entrySet()) {
        enchantments.put(Enchantment.getByName(entry.getKey()), (int) entry.getValue());
      }

      itemBuilder = itemBuilder.enchantments(enchantments);
    }

    if (itemNode.has("tags")) {
      itemBuilder = itemBuilder.tags(objectMapper.convertValue(itemNode.get("tags"), Map.class));
    }

    return MenuItem
      .builder()
      .item(itemBuilder.build())
      .slot(item.get("slot").asInt())
      .build();
  }
}
