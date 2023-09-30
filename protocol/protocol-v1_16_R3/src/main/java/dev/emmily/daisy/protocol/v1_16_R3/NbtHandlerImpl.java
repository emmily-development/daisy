package dev.emmily.daisy.protocol.v1_16_R3;

import dev.emmily.daisy.api.protocol.NbtHandler;
import net.minecraft.server.v1_16_R3.NBTBase;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class NbtHandlerImpl
  implements NbtHandler {
  @Override
  public ItemStack addTag(ItemStack item,
                          String key,
                          Object value) {
    net.minecraft.server.v1_16_R3.ItemStack copy = CraftItemStack.asNMSCopy(item);
    NBTTagCompound compound = copy.getTag();

    if (compound == null) {
      compound = new NBTTagCompound();
      copy.setTag(compound);
    }

    if (value instanceof Byte $short) {
      compound.setByte(key, $short);
    } else if (value instanceof Short $short) {
      compound.setShort(key, $short);
    } else if (value instanceof Integer $int) {
      compound.setInt(key, $int);
    } else if (value instanceof Long $long) {
      compound.setLong(key, $long);
    } else if (value instanceof Float $float) {
      compound.setFloat(key, $float);
    } else if (value instanceof Double $double) {
      compound.setDouble(key, $double);
    } else if (value instanceof byte[] $bytes) {
      compound.setByteArray(key, $bytes);
    } else if (value instanceof String $string) {
      compound.setString(key, $string);
    } else if (value instanceof int[] $ints) {
      compound.setIntArray(key, $ints);
    }

    return CraftItemStack.asBukkitCopy(copy);
  }

  @Override
  public boolean hasTag(ItemStack item,
                        String key) {
    NBTTagCompound compound = CraftItemStack.asNMSCopy(item).getTag();

    return compound != null && compound.hasKey(key);
  }

  @Override
  public Map<String, Object> getTags(ItemStack item) {
    Map<String, Object> tags = new HashMap<>();

    NBTTagCompound compound = CraftItemStack.asNMSCopy(item).getTag();

    if (compound == null) {
      return tags;
    }

    for (String key : compound.getKeys()) {
      NBTBase nbtBase = compound.get(key);

      if (nbtBase == null) {
        continue;
      }

      tags.put(key, switch (nbtBase.getTypeId()) {
        case 1 -> compound.getByte(key);
        case 2 -> compound.getShort(key);
        case 3 -> compound.getInt(key);
        case 4 -> compound.getLong(key);
        case 5 -> compound.getFloat(key);
        case 6 -> compound.getDouble(key);
        case 7 -> compound.getByteArray(key);
        case 8 -> compound.getString(key);
        case 11 -> compound.getIntArray(key);
        default -> throw new IllegalStateException("Unexpected value: " + nbtBase.getTypeId());
      });
    }

    return tags;
  }
}
