package dev.emmily.daisy.protocol.v1_19_R3;

import dev.emmily.daisy.api.protocol.NbtHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class NbtHandlerImpl
  implements NbtHandler {
  @Override
  public ItemStack addTag(ItemStack item,
                          String key,
                          Object value) {
    net.minecraft.world.item.ItemStack copy = CraftItemStack.asNMSCopy(item);
    NBTTagCompound compound = copy.u();

    if (compound == null) {
      compound = new NBTTagCompound();
      copy.c(compound);
    }

    if (value instanceof Byte $byte) {
      compound.a(key, $byte);
    } else if (value instanceof Short $short) {
      compound.a(key, $short);
    } else if (value instanceof Integer $int) {
      compound.a(key, $int);
    } else if (value instanceof Long $long) {
      compound.a(key, $long);
    } else if (value instanceof Float $float) {
      compound.a(key, $float);
    } else if (value instanceof Double $double) {
      compound.a(key, $double);
    } else if (value instanceof byte[] $bytes) {
      compound.a(key, $bytes);
    } else if (value instanceof String $string) {
      compound.a(key, $string);
    } else if (value instanceof int[] $ints) {
      compound.a(key, $ints);
    }

    return CraftItemStack.asBukkitCopy(copy);
  }

  @Override
  public boolean hasTag(ItemStack item,
                        String key) {
    NBTTagCompound compound = CraftItemStack.asNMSCopy(item).u();

    return compound != null && compound.e(key);
  }

  @Override
  public byte getByte(ItemStack item,
                      String key) {
    NBTTagCompound compound = CraftItemStack.asNMSCopy(item).u();

    if (compound == null) {
      return 0;
    }

    return compound.f(key);
  }

  @Override
  public short getShort(ItemStack item,
                        String key) {
    NBTTagCompound compound = CraftItemStack.asNMSCopy(item).u();

    if (compound == null) {
      return 0;
    }

    return compound.g(key);
  }

  @Override
  public int getInt(ItemStack item,
                    String key) {
    NBTTagCompound compound = CraftItemStack.asNMSCopy(item).u();

    if (compound == null) {
      return 0;
    }

    return compound.h(key);
  }

  @Override
  public long getLong(ItemStack item,
                      String key) {
    NBTTagCompound compound = CraftItemStack.asNMSCopy(item).u();

    if (compound == null) {
      return 0;
    }

    return compound.i(key);
  }

  @Override
  public float getFloat(ItemStack item,
                        String key) {
    NBTTagCompound compound = CraftItemStack.asNMSCopy(item).u();

    if (compound == null) {
      return 0;
    }

    return compound.j(key);
  }

  @Override
  public double getDouble(ItemStack item,
                          String key) {
    NBTTagCompound compound = CraftItemStack.asNMSCopy(item).u();

    if (compound == null) {
      return 0;
    }

    return compound.k(key);
  }

  @Override
  public byte[] getByteArray(ItemStack item,
                             String key) {
    NBTTagCompound compound = CraftItemStack.asNMSCopy(item).u();

    if (compound == null) {
      return new byte[0];
    }

    return compound.m(key);
  }

  @Override
  public String getString(ItemStack item,
                          String key) {
    NBTTagCompound compound = CraftItemStack.asNMSCopy(item).u();

    if (compound == null) {
      return "";
    }

    return compound.l(key);
  }

  @Override
  public int[] getIntArray(ItemStack item,
                           String key) {
    NBTTagCompound compound = CraftItemStack.asNMSCopy(item).u();

    if (compound == null) {
      return new int[0];
    }

    return compound.n(key);
  }

  @Override
  public Map<String, Object> getTags(ItemStack item) {
    Map<String, Object> tags = new HashMap<>();

    NBTTagCompound compound = CraftItemStack.asNMSCopy(item).u();

    if (compound == null) {
      return tags;
    }

    for (String key : compound.e()) {
      NBTBase nbtBase = compound.c(key);

      if (nbtBase == null) {
        continue;
      }

      tags.put(key, switch (nbtBase.b()) {
        case 1 -> compound.f(key);
        case 2 -> compound.g(key);
        case 3 -> compound.h(key);
        case 4 -> compound.i(key);
        case 5 -> compound.j(key);
        case 6 -> compound.k(key);
        case 7 -> compound.m(key);
        case 8 -> compound.l(key);
        case 11 -> compound.n(key);
        default -> throw new IllegalStateException("Unexpected value: " + nbtBase.a());
      });
    }

    return tags;
  }
}

