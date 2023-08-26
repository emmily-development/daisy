package dev.emmily.daisy.util;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public interface SoundPlayer {
  static void playSound(Player player,
                        Sound sound,
                        float volume,
                        float pitch) {
    player.playSound(
      player.getLocation(),
      sound, volume, pitch
    );
  }
}
