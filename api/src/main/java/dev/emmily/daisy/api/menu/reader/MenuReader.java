package dev.emmily.daisy.api.menu.reader;

import dev.emmily.daisy.api.menu.Menu;

import java.io.File;
import java.io.IOException;

/**
 * An abstract menu reader able to
 * create menus from different input
 * types, such as files or strings.
 *
 * <p>
 * Each implementation must:
 *
 *   <ol>
 *     <li>Implement a single file format</li>
 *     <li>
 *       Be able to transform the content of
 *       a file into a menu successfully
 *     </li>
 *   </ol>
 * </p>
 */
public interface MenuReader {
  Menu readFromFile(File file) throws IOException;

  Menu readFromString(String string) throws IOException;
}
