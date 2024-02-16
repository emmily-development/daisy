package dev.emmily.daisy.protocol.v1_20_R3;

import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.protocol.title.TitleUpdater;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.Containers;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftContainer;
import org.bukkit.craftbukkit.v1_20_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;

public class TitleUpdaterImpl
  implements TitleUpdater {
  @Override
  public void updateTitle(Player player,
                          Menu menu,
                          String newTitle) {
    EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
    Containers<?> containers = CraftContainer.getNotchInventoryType(menu.getInventory());

    Container container = nmsPlayer.bS;
    nmsPlayer.c.b(new PacketPlayOutOpenWindow(
      container.j,
      containers,
      CraftChatMessage.fromString(newTitle)[0]
    ));
    player.updateInventory();
  }
}
