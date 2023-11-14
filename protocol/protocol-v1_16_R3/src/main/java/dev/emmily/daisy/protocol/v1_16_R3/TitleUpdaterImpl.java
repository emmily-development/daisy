package dev.emmily.daisy.protocol.v1_16_R3;

import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.protocol.title.TitleUpdater;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
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
      if (nmsId.equals("minecraft:chest"))   {
        int rows = menu.getRows();
        int columns = menu.getColumns();
        containers = IRegistry.MENU.get(new MinecraftKey("generic_" + columns + "x" + rows));
      } else {
        containers = IRegistry.MENU.get(new MinecraftKey(nmsId.replace("minecraft:", "")));
      }
    }

    nmsPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(
      nmsPlayer.activeContainer.windowId,
      containers,
      new ChatMessage(newTitle)
    ));
    nmsPlayer.updateInventory(nmsPlayer.activeContainer);
  }
}
