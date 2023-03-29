package com.ts;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class JsonUtil {
  private static final ObjectMapper camelCaseObjectMapper =
      new ObjectMapper()
          .setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)
          .configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

  public static <T> T parse(
      String content, TypeReference<T> valueTypeRef, ObjectMapper objectMapper) {
    if (content == null) {
      return null;
    }
    try {
      return objectMapper.readValue(content, valueTypeRef);
    } catch (Exception e) {
      throw new RuntimeException(
          String.format("Failed to parse to %s with json %s", valueTypeRef, content), e);
    }
  }

  public static <T> String format(T value, ObjectMapper objectMapper) {
    if (value == null) {
      return null;
    }
    try {
      return objectMapper.writeValueAsString(value);
    } catch (Exception e) {
      throw new RuntimeException(String.format("Failed to format %s to json", value), e);
    }
  }

  public static <T> T parseCamelCase(String content, TypeReference<T> valueTypeRef) {
    return parse(content, valueTypeRef, camelCaseObjectMapper);
  }

  public static <T> String formatCamelCase(T value) {
    return format(value, camelCaseObjectMapper);
  }
}
