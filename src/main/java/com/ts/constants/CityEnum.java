package com.ts.constants;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CityEnum {
  BEIJING(1, "北京"),
  GUANGZHOU(4, "广州");

  private static final Map<Integer, CityEnum> idToEnumMap = new HashMap<>();

  static {
    for (CityEnum item : CityEnum.values()) {
      idToEnumMap.put(item.id, item);
    }
  }

  public static CityEnum fromId(Integer id) {
    return idToEnumMap.get(id);
  }

  @Getter private Integer id;
  @Getter private String name;
}
