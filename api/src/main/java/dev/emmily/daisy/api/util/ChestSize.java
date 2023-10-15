package dev.emmily.daisy.api.util;

import com.google.common.base.Preconditions;

/**
 * Utility class to convert slots to
 * chests rows and vice-versa.
 */
public interface ChestSize {
  static int toRows(int slots) {
    Preconditions.checkArgument(slots % 9 == 0, "not a multiple of 9: %s", slots);

    return slots / 9;
  }

  static int toSlots(int rows) {
    return rows * 9;
  }
}
