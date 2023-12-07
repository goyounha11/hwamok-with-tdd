package com.hwamok.core;

import org.apache.logging.log4j.util.Strings;

import java.util.Objects;

public class Preconditions {

  public static <T> T notNull(T obj) {
    return Objects.requireNonNull(obj);
  }

  public static void require(boolean expression) {
    if (!expression) {
      throw new IllegalArgumentException();
    }
  }
}
