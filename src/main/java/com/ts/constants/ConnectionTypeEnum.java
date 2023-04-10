package com.ts.constants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ConnectionTypeEnum {
  // 站点间连线类型.用于绘图
  HORIZONTAL, // 只支持从左往右
  VERTICAL, // 只支持上到下
  LEFT_TO_TOP,
  TOP_TO_RIGHT,
  RIGHT_TO_BOTTOM,
  BOTTOM_TO_LEFT
}
