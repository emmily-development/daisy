package dev.emmily.daisy.action;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.beans.ConstructorProperties;

public class PlaySoundAction
  implements Action {
  private final Sound sound;
  private final float volume;
  private final float pitch;

  @ConstructorProperties({
    "sound", "volume", "pitch"
  })
  public PlaySoundAction(Sound sound,
                         float volume,
                         float pitch) {
    this.sound = sound;
    this.volume = volume;
    this.pitch = pitch;
  }

  @Override
  public boolean perform(Object entity) {
    if (!(entity instanceof Player)) {
      return false;
    }

    Player player = (Player) entity;
    player.playSound(player.getLocation(), sound, volume, pitch);

    return true;
  }

  public Sound getSound() {
    return sound;
  }

  public float getVolume() {
    return volume;
  }

  public float getPitch() {
    return pitch;
  }
}
