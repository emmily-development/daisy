package dev.emmily.daisy.protocol.v1_19_R3;

import dev.emmily.daisy.api.menu.Menu;
import dev.emmily.daisy.api.protocol.title.TitleUpdater;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.Containers;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftContainer;
import org.bukkit.entity.Player;

public class TitleUpdaterImpl
  implements TitleUpdater {
  @Override
  public void updateTitle(Player player,
                          Menu menu,
                          String newTitle) {
    EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
    Containers<?> containers = CraftContainer.getNotchInventoryType(menu.getInventory());
    Container container = nmsPlayer.bP;

    nmsPlayer.b.a(new PacketPlayOutOpenWindow(
      container.j,
      containers,
      IChatBaseComponent.a(newTitle)
    ));

    player.updateInventory();
  }
}
