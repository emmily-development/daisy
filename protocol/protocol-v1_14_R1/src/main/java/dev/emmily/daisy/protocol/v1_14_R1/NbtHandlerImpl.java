package dev.emmily.daisy.protocol.v1_14_R1;

import dev.emmily.daisy.api.protocol.NbtHandler;
import net.minecraft.server.v1_14_R1.NBTBase;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class NbtHandlerImpl
  implements NbtHandler {
  @Override
  public ItemStack addTag(ItemStack item,
                          String key,
                          Object value) {
    net.minecraft.server.v1_14_R1.ItemStack copy = CraftItemStack.asNMSCopy(item);
    NBTTagCompound compound = copy.getTag();

    if (compound == null) {
      compound = new NBTTagCompound();
      copy.setTag(compound);
    }

    if (value instanceof Byte) {
      compound.setByte(key, (byte) value);
    } else if (value instanceof Short) {
      compound.setShort(key, (short) value);
    } else if (value instanceof Integer) {
      compound.setInt(key, (int) value);
    } else if (value instanceof Long) {
      compound.setLong(key, (long) value);
    } else if (value instanceof Float) {
      compound.setFloat(key, (float) value);
    } else if (value instanceof Double) {
      compound.setDouble(key, (double) value);
    } else if (value instanceof byte[]) {
      compound.setByteArray(key, (byte[]) value);
    } else if (value instanceof String) {
      compound.setString(key, (String) value);
    } else if (value instanceof int[]) {
      compound.setIntArray(key, (int[]) value);
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

      Object value = null;

      switch (nbtBase.getTypeId()) {
        case 1: {
          value = compound.getByte(key);

          break;
        }

        case 2: {
          value = compound.getShort(key);

          break;
        }

        case 3: {
          value = compound.getInt(key);

          break;
        }

        case 4: {
          value = compound.getLong(key);

          break;
        }

        case 5: {
          value = compound.getFloat(key);

          break;
        }

        case 6: {
          value = compound.getDouble(key);

          break;
        }

        case 7: {
          value = compound.getByteArray(key);

          break;
        }

        case 8: {
          value = compound.getString(key);

          break;
        }
        case 11: {
          value = compound.getIntArray(key);

          break;
        }
      }

      tags.put(key, value);
    }

    return tags;
  }
}