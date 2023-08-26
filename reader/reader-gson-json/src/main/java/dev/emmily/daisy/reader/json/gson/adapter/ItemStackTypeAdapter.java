package dev.emmily.daisy.reader.json.gson.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ItemStackTypeAdapter
  extends TypeAdapter<ItemStack> {
  org.bukkit.Bukkit
  @Override
  public void write(JsonWriter out, ItemStack value) throws IOException {

  }

  @Override
  public ItemStack read(JsonReader in) throws IOException {
    return null;
  }
}
