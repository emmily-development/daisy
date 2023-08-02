package dev.emmily.daisy.action;

import org.bukkit.entity.Player;

import java.beans.ConstructorProperties;

public class SendMessageAction
  implements Action {
  private final String message;

  @ConstructorProperties("message")
  public SendMessageAction(String message) {
    this.message = message;
  }

  @Override
  public boolean perform(Object entity) {
    if (!(entity instanceof Player)) {
      return false;
    }

    ((Player) entity).sendMessage(message);

    return true;
  }

  public String getMessage() {
    return message;
  }
}
