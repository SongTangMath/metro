package com.ts.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ts.constants.MetroStationLineTypeEnum;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MetroInfo {
  private Integer cityId;
  private String cityName;
  // 当前城市站点数
  private Integer stationCount;

  private List<MetroLine> lines;
  private List<MetroStation> stations;

  public void calculateStationCount() {
    this.stationCount = stations.size();
    for (MetroLine line : lines) {
      int stationCount = line.getStationNames().size();
      if (!CollectionUtils.isEmpty(line.getBranchLines())) {
        for (MetroBranchLine branchLine : line.getBranchLines()) {
          stationCount += branchLine.getBranchLineStationNames().size() - 1;
        }
      }
      line.setStationCount(stationCount);
      // 此接口不需要站点详情
      this.stations = null;
    }
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  @Builder
  @JsonInclude(Include.NON_NULL)
  public static class MetroStation {
    private Integer stationId;
    private String stationName;
    // 经度
    private Double longitude;
    // 维度
    private Double latitude;

    private List<Integer> lineIds;
    // 如果为换乘站,拼接换乘站的信息.不包括当条线路.例如13号线立水桥换5号线则transferLines中只有5号线
    private List<MetroTransferLine> transferLines;
    // 绘图信息
    // 会被序列化为"xpos"
    private Double xPos;
    private Double yPos;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  @Builder
  public static class MetroTransferLine {
    private Integer lineId;
    private String lineName;
    private String color;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  @Builder
  public static class MetroLine {

    private Integer cityId;
    private Integer lineId;
    private String lineName;
    // #开头的16进制颜色
    private String color;

    // 主线上的站点
    private List<String> stationNames;
    // 是否为环线,如北京10号线为环线
    private Boolean isLoopLine;
    // 对于环线,绘图的时候需要更多信息.环线将会绘制为一个矩形.按照上右下左的顺序绘制站点,和计算站点位置
    private List<String> topStationNames;
    private List<String> rightStationNames;
    private List<String> bottomStationNames;
    private List<String> leftStationNames;

    // 似乎暂时还没有多支线的线路
    private List<MetroBranchLine> branchLines;
    // 当前线路的总站点数
    private Integer stationCount;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  @Builder
  public static class MetroBranchLine {
    // 支线名称.如 "3号线北延段" (广州体育西路->机场北)
    private String branchLineName;
    private List<String> branchLineStationNames;

    private MetroStation splitStation;
    // 除去分叉的那一站外的其它站
    private List<MetroStation> branchLineStations;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  @Builder
  public static class SingleLineMetroInfo {
    private Integer lineId;
    private String lineName;
    private String color;
    private List<MetroStation> stations;

    // 绘图数据.绘制站点间连线
    private List<MetroStationConnection> stationConnections;
    // 换乘站标志.
    private List<MetroStationConnection> stationTransfers;
    private List<MetroStationTransferText> transferTexts;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  @Builder
  public static class MetroStationTransferText {
    private String text;
    private Double xPos;

    private Double yPos;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  @Builder
  public static class MetroStationConnection {

    // for debug
    private String name;
    // 当前区域要填充的颜色
    private String color;
    private List<MetroStationConnectionLine> lines;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  @Builder
  @JsonInclude(Include.NON_NULL)
  public static class MetroStationConnectionLine {
    // "line","arc"
    private String lineType;
    private Double lineStartX;
    private Double lineStartY;
    private Double lineEndX;
    private Double lineEndY;

    private Double arcCenterX;
    private Double arcCenterY;
    private Double radius;
    private Double arcStartAngle;
    private Double arcEndAngle;
    private Boolean isCounterClockWise;

    public static MetroStationConnectionLine constructFromPoints(Point point1, Point point2) {
      return MetroStationConnectionLine.builder()
          .lineType(MetroStationLineTypeEnum.LINE.getIdentifier())
          .lineStartX(point1.getX())
          .lineStartY(point1.getY())
          .lineEndX(point2.getX())
          .lineEndY(point2.getY())
          .build();
    }
  }
}
