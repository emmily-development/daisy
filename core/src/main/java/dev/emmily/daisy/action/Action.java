package dev.emmily.daisy.action;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Represents an action that an object
 * can perform.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public interface Action {
  static Action alwaysTrue() {
    return entity -> true;
  }

  static Action alwaysFalse() {
    return entity -> false;
  }

  /**
   * Merges multiple actions in a single one.
   * The final condition returns {@code true} if
   * and only if every single one of the given
   * {@code actions} return true.
   *
   * @param actions The actions to be merged
   *                in a single one.
   * @return {@code true} if and only if every single
   * one of the given {@code actions} return true.
   */
  static Action and(Action... actions) {
    return entity -> {
      for (Action action : actions) {
        if (!action.perform(entity)) {
          return false;
        }
      }

      return true;
    };
  }

  /**
   * Returns {@code true} if the given {@code entity}
   * performed the contextual action successfully.
   *
   * @param entity The entity to be checked.
   * @return {@code true} if the given {@code entity}
   * performed the contextual action successfully.
   */
  boolean perform(Object entity);
}
