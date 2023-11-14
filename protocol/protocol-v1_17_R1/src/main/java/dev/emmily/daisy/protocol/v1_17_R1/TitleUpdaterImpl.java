package dev.emmily.daisy.protocol.v1_17_R1;

import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.protocol.title.TitleUpdater;
import net.minecraft.core.IRegistry;
import net.minecraft.network.chat.ChatMessage;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.network.protocol.game.PacketPlayOutWindowItems;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.Containers;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftContainer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.EnumSet;

public class TitleUpdaterImpl
  implements TitleUpdater {
  @Override
  public void updateTitle(Player player,
                          Menu menu,
                          String newTitle) {
    EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
    Containers<?> containers = CraftContainer.getNotchInventoryType(menu.getInventory());
    Container container = nmsPlayer.bV;

    nmsPlayer.b.sendPacket(new PacketPlayOutOpenWindow(
      container.j,
      containers,
      new ChatMessage(newTitle)
    ));

    player.updateInventory();
  }
}
