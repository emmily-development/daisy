package dev.emmily.daisydemo;

import dev.emmily.daisy.api.item.MenuItem;
import dev.emmily.daisy.api.item.builder.ItemBuilder;
import dev.emmily.daisy.api.item.color.ItemColor;
import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.menu.reader.MenuReader;
import dev.emmily.daisy.api.menu.reader.loader.MenuLoader;
import dev.emmily.daisy.api.menu.types.chest.ChestSize;
import dev.emmily.daisy.api.menu.types.dynamic.registry.DynamicMenuRegistry;
import dev.emmily.daisy.core.DaisySetup;
import dev.emmily.daisy.core.util.SoundPlayer;
import dev.emmily.daisydemo.config.ConfigurationFile;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// TODO: Create test menus using a paginated selector menu
// TODO: Load custom menus on the plugin startup
// TODO: Add maven shade plugin XD
public class DaisyDemoPlugin
  extends JavaPlugin {
  private File menusFolder;
  private DynamicMenuRegistry dynamicMenuRegistry;
  private ConfigurationFile config;
  private MenuReader menuReader;
  private MenuLoader menuLoader;

  @Override
  public void onEnable() {
    dynamicMenuRegistry = new DynamicMenuRegistry();
    DaisySetup.injectListeners(this, dynamicMenuRegistry);
    DaisySetup.injectScheduler(this, dynamicMenuRegistry);

    config = new ConfigurationFile(this, "config");

    /*
    String readerName = config.getString("menu-reader");
    switch (readerName) {
      case "jackson": {
        menuReader = new dev.emmily.daisy.reader.json.jackson.JsonMenuReader();

        break;
      }

      case "gson":
      default: {
        menuReader = new dev.emmily.daisy.reader.json.gson.JsonMenuReader();

        break;
      }
    }

    getLogger().info("Using the " + readerName + " reader.");


     */
    menusFolder = new File(getDataFolder(), "menus");
    menuLoader = new MenuLoader(this, menuReader);

    getCommand("daisy").setExecutor((commandSender, command, label, args) -> {
      if (!(commandSender instanceof Player)) {
        commandSender.sendMessage("This command is available for players only!");

        return false;
      }

      Player player = (Player) commandSender;
      String type = null;

      if (args.length != 0) {
        type = args[0];
      }

      if (type == null) {
        player.sendMessage(ChatColor.translateAlternateColorCodes(
          '&', "&dUsage: &e/daisy <default|layout|paginated|anvil|enchantment>"
        ));

        return true;
      }

      switch (type) {
        case "default": {
          openSimpleMenu(player);

          break;
        }

        case "layout": {
          openLayoutMenu(player);

          break;
        }

        case "updatable": {
          openDynamicMenu(player);

          break;
        }

        case "updatable-layout": {
          openDynamicLayoutMenu(player);

          break;
        }

        /*
        case "file-updatable": {
          if (args[1] == null || args[2] == null) {
            player.sendMessage("Unknown menu");

            return false;
          }

          openUpdatableMenuFromFile(player, args[1], args[2]);

          break;
        }

         */

        case "paginated": {
          openPaginatedMenu(player);

          break;
        }
      }

      return true;
    });
  }

  @Override
  public void onDisable() {
    config.save();
  }

  /*
  private List<String> generateStrings(int amount) {
    List<String> strings = new ArrayList<>(amount);

    for (int i = 0; i < amount; i++) {
      strings.add("Test - " + i);
    }

    return strings;
  }

   */

  private void openSimpleMenu(Player player) {
    player.openInventory(Menu
      .chestMenuBuilder()
      .title("Daisy demo plugin")
      .size(ChestSize.toSlots(3))
      .openAction(event -> {
        player.sendMessage("Opening a menu!");
        SoundPlayer.playSound(player, Sound.NOTE_PLING, 1, 1);

        return true;
      })
      .closeAction(event -> player.sendMessage("Closing the menu!"))
      .addItems(
        MenuItem
          .builder()
          .slot(13)
          .item(ItemBuilder
            .builder()
            .type(Material.STAINED_GLASS_PANE)
            .data(ItemColor.CYAN.getMetadataBits())
            .amount(1)
            .name("&3Just a colored glass pane")
            .lore(
              "&7First line of lore",
              "&7Second line of lore"
            )
            .build()
          )
          .action(event -> {
            SoundPlayer.playSound(player, Sound.CLICK, 1, 1);

            return true;
          })
          .build()
      )
      .build()
      .getInventory()
    );
  }

  private void openLayoutMenu(Player player) {
    player.openInventory(Menu
      .layoutMenuBuilder()
      .addType(Menu.Type.CHEST)
      .title("Layout-based menu")
      .openAction(event -> {
        player.sendMessage("Opening a layout-based menu!");

        return true;
      })
      .closeAction(event -> player.sendMessage("Closing the menu!"))
      .layout(
        "d-d-d-d-d",
        "---------",
        "d---x---d",
        "---------",
        "d-d-d-d-d"
      )
      .addItem('d', MenuItem
        .builder()
        .item(ItemBuilder
          .builder()
          .type(Material.STAINED_GLASS_PANE)
          .data(ItemColor.BLACK.getMetadataBits())
          .name("")
          .build()
        )
        .action(event -> true)
        .build()
      )
      .addItem('x', MenuItem
        .builder()
        .item(ItemBuilder
          .builder()
          .type(Material.FEATHER)
          .name("Test item")
          .lore(
            "&7This menu is based",
            "&7on a text layout"
          )
          .build()
        )
        .action(event -> true)
        .build()
      )
      .build()
      .getInventory()
    );
  }

  private void openDynamicMenu(Player player) {
    AtomicInteger updates = new AtomicInteger();

    player.openInventory(Menu
      .dynamicMenuBuilder()
      .addType(Menu.Type.CHEST)
      .title("Updatable menu")
      .size(ChestSize.toSlots(3))
      .updatePeriod(TimeUnit.SECONDS.toMillis(1))
      .addFrame(Collections.singletonList(MenuItem
        .builder()
        .slot(13)
        .item(ItemBuilder
          .builder()
          .type(Material.BLAZE_ROD)
          .name("Test (frame 1)")
          .lore("&7Test line")
          .build()
        )
        .action(event -> true)
        .build()
      ))
      .addFrame(Collections.singletonList(MenuItem
        .builder()
        .slot(13)
        .item(ItemBuilder
          .builder()
          .type(Material.ENDER_PEARL)
          .name("Test (frame 2)")
          .lore("&7Test line")
          .build()
        )
        .action(event -> true)
        .build()
      ))
      .updateAction(menu -> updates.getAndIncrement())
      .addSuppliableItem(() -> MenuItem
        .builder()
        .slot(22)
        .item(ItemBuilder
          .builder()
          .type(Material.COOKIE)
          .name("Update #" + updates)
          .build()
        )
        .action(event -> true)
        .build()
      )
      .build()
      .getInventory()
    );
  }

  public void openDynamicLayoutMenu(Player player) {
    player.openInventory(Menu
      .dynamicLayoutMenuBuilder()
      .addTypes(Menu.Type.CHEST)
      .title("Updatable menu")
      .size(ChestSize.toSlots(5))
      .updatePeriod(TimeUnit.SECONDS.toMillis(1))
      .addFrame(
        "dDdDdDdDd",
        "D-------D",
        "d---1---d",
        "D-------D",
        "dDdDdDdDd"
      )
      .addFrame(
        "DdDdDdDdD",
        "d-------d",
        "D---2---D",
        "d-------d",
        "DdDdDdDdD"
      )
      .addItem('1', MenuItem
        .builder()
        .item(ItemBuilder
          .builder()
          .type(Material.WATCH)
          .name("Test (frame 1)")
          .lore("&7Test line")
          .build()
        )
        .action(event -> true)
        .build()
      )
      .addItem('2', MenuItem
        .builder()
        .item(ItemBuilder
          .builder()
          .type(Material.GOLD_INGOT)
          .name("Test (frame 2)")
          .lore("&7Test line")
          .build()
        )
        .action(event -> true)
        .build()
      )
      .addItem('D', MenuItem
        .builder()
        .item(ItemBuilder
          .builder()
          .type(Material.STAINED_GLASS_PANE)
          .data(ItemColor.BLUE.getMetadataBits())
          .name("")
          .build()
        )
        .action(event -> true)
        .build()
      )
      .addItem('d', MenuItem
        .builder()
        .item(ItemBuilder
          .builder()
          .type(Material.STAINED_GLASS_PANE)
          .data(ItemColor.RED.getMetadataBits())
          .name("")
          .build()
        )
        .action(event -> true)
        .build()
      )
      .build()
      .getInventory()
    );
  }

  private void openUpdatableMenuFromFile(Player player,
                                         String filename,
                                         String resourceName) {
    try {
      player.openInventory(menuLoader
        .load(filename, resourceName)
        .getInventory()
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void openPaginatedMenu(Player player) {
    player.openInventory(Menu
      .<Integer>paginatedMenuBuilder()
      .addType(Menu.Type.CHEST)
      //.title(page -> "Page #" + page)
      .title("Paginated menu")
      .size(ChestSize.toSlots(3))
      .elements(Arrays.asList(
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
        11, 14, 15, 16, 17, 18, 19, 20
      ))
      .skippedSlots(new ArrayList<>(Arrays.asList(
        0, 1, 2, 3, 4, 5, 6, 7, 8,
        9, 17,
        18, 19, 20, 21, 22, 23, 24, 25, 26
      )))
      //.skippedSlots(skippedSlots)
      .elementsPerPage(7)
      .elementParser((value, index) -> MenuItem
        .builder()
        .item(ItemBuilder
          .builder()
          .type(Material.NAME_TAG)
          .amount(1)
          .name("Item #" + index)
          .lore("&7Value: " + value)
          .build()
        )
        .action(event -> {
          player.sendMessage("You've clicked the item #" + index);
          return true;
        })
        .build()
      )
      .previousPageSwitch(MenuItem
        .builder()
        .slot(18)
        .item(ItemBuilder
          .builder()
          .type(Material.ARROW)
          .name("Previous page")
          .amount(1)
          .build()
        )
        .action(event -> true)
        .build()
      )
      .nextPageSwitch(MenuItem
        .builder()
        .slot(26)
        .item(ItemBuilder
          .builder()
          .type(Material.ARROW)
          .name("Next page")
          .amount(1)
          .build()
        )
        .action(event -> true)
        .build()
      )
      .build()
      .getInventory()
    );
  }
}
