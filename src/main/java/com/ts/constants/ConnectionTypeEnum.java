package com.ts.constants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ConnectionTypeEnum {
  // 站点间连线类型.用于绘图
  HORIZONTAL,
  LEFT_TO_TOP,
  TOP_TO_RIGHT,
  RIGHT_TO_BOTTOM,
  BOTTOM_TO_RIGHT
}
