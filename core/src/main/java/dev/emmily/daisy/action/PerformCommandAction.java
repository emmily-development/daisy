package dev.emmily.daisy.action;

import org.bukkit.entity.Player;

import java.beans.ConstructorProperties;

public class PerformCommandAction
  implements Action {
  private final String command;

  @ConstructorProperties("command")
  public PerformCommandAction(String command) {
    this.command = command;
  }

  @Override
  public boolean perform(Object entity) {
    if (!(entity instanceof Player)) {
      return false;
    }

    return ((Player) entity).performCommand(command);
  }

  public String getCommand() {
    return command;
  }
}
