package dev.emmily.daisy.demo;

import dev.emmily.daisy.DaisySetup;
import dev.emmily.daisy.item.MenuItem;
import dev.emmily.daisy.item.builder.ItemBuilder;
import dev.emmily.daisy.item.color.ItemColor;
import dev.emmily.daisy.menu.Menu;
import dev.emmily.daisy.menu.types.chest.ChestSize;
import dev.emmily.daisy.util.SoundPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

// TODO: Create test menus using a paginated selector menu
// TODO: Load custom menus on the plugin startup
// TODO: Add maven shade plugin XD
public class DaisyDemoPlugin
  extends JavaPlugin {
  @Override
  public void onEnable() {
    DaisySetup.injectListeners(this);

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
      }

      return true;
    });
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
}
