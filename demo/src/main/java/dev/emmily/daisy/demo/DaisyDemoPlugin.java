package dev.emmily.daisy.demo;

import dev.emmily.daisy.DaisySetup;
import dev.emmily.daisy.action.Action;
import dev.emmily.daisy.item.MenuItem;
import dev.emmily.daisy.item.builder.ItemBuilder;
import dev.emmily.daisy.item.color.ItemColor;
import dev.emmily.daisy.menu.Menu;
import dev.emmily.daisy.menu.types.chest.ChestSize;
import dev.emmily.daisy.protocol.NbtHandler;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: Create test menus using a paginated selector menu
// TODO: Load custom menus on the plugin startup
// TODO: Add maven shade plugin XD
public class DaisyDemoPlugin
  extends JavaPlugin {
  @Override
  public void onEnable() {
    DaisySetup.injectListeners(this);

    NbtHandler nbtHandler = DaisySetup.NBT_HANDLER;

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

      if (type == null || type.equals("default")) {
        player.openInventory(Menu
          .chestMenuBuilder()
          .title("Daisy demo plugin")
          .size(ChestSize.toSlots(3))
          .openAction(Action.and(
            Action.sendMessage("Opening a menu!"),
            Action.playSound(Sound.NOTE_PLING, 1, 1)
          ))
          .closeAction(Action.sendMessage("Closing a menu!"))
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
              .action(Action.playSound(Sound.CLICK, 1, 1))
              .build(nbtHandler)
          )
          .build()
          .getInventory()
        );

        return true;
      }

      switch (type) {
        case "layout": {
          player.openInventory(Menu
            .layoutMenuBuilder()
            .layout(Arrays.asList(
              "---------",
              "----x----",
              "---------"
            ))
            .addItem('-', MenuItem
              .builder()
              .item(ItemBuilder
                .builder()
                .type(Material.STAINED_GLASS_PANE)
                .data(ItemColor.BLACK.getMetadataBits())
                .amount(1)
                .name(" ")
                .build()
              )
              .action(entity -> true)
              .build(nbtHandler)
            )
            .addItem('x', MenuItem
              .builder()
              .item(ItemBuilder
                .builder()
                .type(Material.FIREWORK)
                .amount(1)
                .name("&9Layout-based menu!")
                .build()
              )
              .action(entity -> true)
              .build(nbtHandler)
            )
            .title("Layout-based menu")
            .build()
            .getInventory()
          );

          break;
        }

        case "paginated": {
          player.openInventory(Menu
            .<String>paginatedMenuBuilder()
            .skippedSlots(
              0, 1, 2, 3, 4, 5, 6, 7, 8,
              17, 26, 35, 45, 53, 52, 51,
              50, 49, 48, 47, 46, 45, 36,
              27, 18, 9
            )
            .delegate(Menu
              .layoutMenuBuilder()
              .layout(Arrays.asList(
                "---------",
                "---------",
                "---------",
                "---------",
                "---------",
                "----x----"
              ))
              .addItem('x', MenuItem
                .builder()
                .item(ItemBuilder
                  .builder()
                  .type(Material.BARRIER)
                  .amount(1)
                  .name("&cClose")
                  .lore(
                    "&7",
                    "&7Click &e&lhere &7to close",
                    "&7"
                  )
                  .build()
                )
                .action(entity -> {
                  player.closeInventory();

                  return true;
                })
                .build(nbtHandler)
              )
              .title("&7Layout paginated menu")
              .build()
            )
            .elementType(String.class)
            .elementParser(s -> MenuItem
              .builder()
              .item(ItemBuilder
                .builder()
                .type(Material.NETHER_BRICK)
                .amount(1)
                .name(s)
                .build()
              )
              .action(entity -> {
                player.sendMessage("&7You clicked " + s);

                return true;
              })
              .build(nbtHandler)
            )
            .elements(generateStrings(320))
            .nextPageItem(MenuItem
              .builder()
              .item(ItemBuilder
                .builder()
                .type(Material.ARROW)
                .amount(1)
                .name("&a&lNext page")
                .build()
              )
              .build(nbtHandler)
            )
            .previousPageItem(MenuItem
              .builder()
              .item(ItemBuilder
                .builder()
                .type(Material.ARROW)
                .amount(1)
                .name("&a&lPrevious page")
                .build()
              )
              .build(nbtHandler)
            )
            .size(ChestSize.toSlots(6))
            .build()
            .getInventory()
          );

          break;
        }
      }

      return true;
    });
  }

  private List<String> generateStrings(int amount) {
    List<String> strings = new ArrayList<>(amount);

    for (int i = 0; i < amount; i++) {
      strings.add("Test - " + i);
    }

    return strings;
  }
}
