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
  private String lineName;
  // 用于计算绘图参数
  @Builder.Default private Integer canvasWidth = 1500;
  @Builder.Default private Integer canvasHeight = 500;
  // 图中站点圆的半径
  @Builder.Default private Double stationRadius = 10.0;
  // 两站间连线宽度.
  @Builder.Default private Double connectionLineWidth = 10.0;
}
