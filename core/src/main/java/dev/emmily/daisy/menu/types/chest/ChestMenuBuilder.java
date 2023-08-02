package dev.emmily.daisy.menu.types.chest;

import com.google.common.base.Preconditions;
import dev.emmily.daisy.menu.Menu;
import dev.emmily.daisy.menu.types.builder.MenuBuilder;

public class ChestMenuBuilder
  extends MenuBuilder<ChestMenu> {
  @Override
  public ChestMenu build() {
    Preconditions.checkArgument(size % 9 == 0, "invalid size for a chest menu: %s", size);
    type(Menu.Type.CHEST);

    return new ChestMenu(
      title, items,
      size, openAction,
      closeAction
    );
  }
}
