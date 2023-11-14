package dev.emmily.daisy.protocol.v1_18_R2;

import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.protocol.title.TitleUpdater;
import net.minecraft.core.IRegistry;
import net.minecraft.network.chat.ChatMessage;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.Containers;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitleUpdaterImpl
  implements TitleUpdater {
  @Override
  public void updateTitle(Player player,
                          Menu menu,
                          String newTitle) {
    EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
    Containers<?> containers = null;
    String nmsId = null;

    for (Menu.Type type : menu.getType()) {
      if (type.isMinecraftType()) {
        nmsId = type.getNmsId();

        break;
      }
    }

    if (nmsId != null) {
      if (nmsId.equals("minecraft:chest")) {
        int rows = menu.getRows();
        int columns = menu.getColumns();
        containers = IRegistry.ag.a(new MinecraftKey("generic_" + columns + "x" + rows));
      } else {
        containers = IRegistry.ag.a(new MinecraftKey(nmsId.replace("minecraft:", "")));
      }
    }

    Container container = nmsPlayer.bV;

    nmsPlayer.b.a(new PacketPlayOutOpenWindow(
      container.j,
      containers,
      new ChatMessage(newTitle)
    ));

    player.updateInventory();
  }
}
