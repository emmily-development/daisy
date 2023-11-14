package dev.emmily.daisy.protocol.v1_15_R1;

import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.protocol.title.TitleUpdater;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftContainer;
import org.bukkit.entity.Player;

public class TitleUpdaterImpl
  implements TitleUpdater {
  @Override
  public void updateTitle(Player player,
                          Menu menu,
                          String newTitle) {
    EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
    Containers<?> containers = CraftContainer.getNotchInventoryType(menu.getInventory());

    nmsPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(
      nmsPlayer.activeContainer.windowId,
      containers,
      new ChatMessage(newTitle)
    ));
    nmsPlayer.updateInventory(nmsPlayer.activeContainer);
  }
}
