package com.ts;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MetroStationLineTypeEnum {
  LINE("line"),
  ARC("arc");

  private static final Map<String, MetroStationLineTypeEnum> identifierToEnumMap = new HashMap<>();

  static {
    for (MetroStationLineTypeEnum item : MetroStationLineTypeEnum.values()) {
      identifierToEnumMap.put(item.identifier, item);
    }
  }

  @Getter private String identifier;
}
