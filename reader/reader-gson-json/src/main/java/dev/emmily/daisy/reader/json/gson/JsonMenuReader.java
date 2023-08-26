package dev.emmily.daisy.reader.json.gson;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.emmily.daisy.menu.Menu;
import dev.emmily.daisy.reader.MenuReader;

import java.io.File;
import java.io.IOException;

public class JsonMenuReader
  implements MenuReader {
  private static final Gson DEFAULT_GSON = new Gson();

  private final Gson gson;

  public JsonMenuReader(Gson gson) {
    this.gson = gson;
  }

  public JsonMenuReader() {
    this(DEFAULT_GSON);
  }

  @Override
  public Menu readFromFile(File file) throws IOException {

    return null;
  }

  @Override
  public Menu readFromString(String string) throws IOException {
    return null;
  }
}
