package dev.emmily.daisy.api.util;

import java.util.Objects;

public interface TetraConsumer<T, U, V, W> {
  void accept(T t, U u, V v, W w);

  default TetraConsumer<T, U, V, W> andThen(TetraConsumer<? super T, ? super U, ? super V, ? super W> after) {
    Objects.requireNonNull(after);
    return (a, b, c, d) -> {
      accept(a, b, c, d);
      after.accept(a, b, c, d);
    };
  }
}
