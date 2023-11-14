package dev.emmily.daisy.protocol.v1_9_R2;

import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.protocol.title.TitleUpdater;
import net.minecraft.server.v1_9_R2.ChatMessage;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.PacketPlayOutOpenWindow;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitleUpdaterImpl
  implements TitleUpdater {
  @Override
  public void updateTitle(Player player,
                          Menu menu,
                          String newTitle) {

    EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();

    String nmsId = null;

    for (Menu.Type type : menu.getType()) {
      if (type.isMinecraftType()) {
        nmsId = type.getNmsId();

        break;
      }
    }
    nmsPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(
      nmsPlayer.activeContainer.windowId,
      nmsId,
      new ChatMessage(newTitle),
      player.getOpenInventory().getTopInventory().getSize()
    ));
    nmsPlayer.updateInventory(nmsPlayer.activeContainer);
  }
}
