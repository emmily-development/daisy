package dev.emmily.daisy.protocol.v1_8_R3;

import dev.emmily.daisy.protocol.NbtHandler;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NbtHandlerImpl
  implements NbtHandler {

  @Override
  public ItemStack addTag(ItemStack item,
                          String key,
                          String value) {
    net.minecraft.server.v1_8_R3.ItemStack copy = CraftItemStack.asNMSCopy(item);
    NBTTagCompound compound = copy.getTag();

    if (compound == null) {
      compound = new NBTTagCompound();
      copy.setTag(compound);
    }

    compound.setString(key, value);

    return CraftItemStack.asBukkitCopy(copy);
  }

  @Override
  public boolean hasTag(ItemStack item,
                        String key) {
    NBTTagCompound compound = CraftItemStack.asNMSCopy(item).getTag();

    return compound != null && compound.hasKey(key);
  }
}
