package dev.emmily.daisy.api.protocol;

import org.bukkit.Bukkit;

public interface ServerVersion {
  String SERVER_VERSION = Bukkit
    .getServer()
    .getClass()
    .getName()
    .split("\\.")[3];
  String PROTOCOL_CLASS_PATTERN = "dev.emmily.daisy.protocol.%s";
}
