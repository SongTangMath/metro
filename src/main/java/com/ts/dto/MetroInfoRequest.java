package com.ts.dto;

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
  @Builder.Default private Integer canvasWidth = 2000;
  @Builder.Default private Integer canvasHeight = 500;
  // 图中站点圆的半径
  @Builder.Default private Double stationRadius = 10.0;
  // 两站间连线宽度.连线宽度不得超过站点圆半径
  @Builder.Default private Double connectionLineWidth = 10.0;

  @Builder.Default private Integer fontSize = 15;
}
