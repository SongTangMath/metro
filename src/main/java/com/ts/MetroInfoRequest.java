package com.ts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MetroInfoRequest {
  private Integer cityId;
  private Integer lineId;
  // 用于计算绘图参数
  private Integer canvasWidth = 1000;
  private Integer canvasHeight = 500;
  // 图中站点圆的半径
  private Integer stationRadius = 10;
}
