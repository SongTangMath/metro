package com.ts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MetroInfo {
  private Integer cityId;
  private String cityName;
  private List<MetroLine> lines;
  private List<MetroStation> stations;

  private Map<String, MetroStation> stationNameToStation;

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
    private List<String> stationNames;
    // 是否为环线,如北京10号线为环线
    private Boolean isLoopLine;
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
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  @Builder
  public static class MetroStationConnection {

    private List<MetroStationConnectionLine> lines;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  @Builder
  public static class MetroStationConnectionLine { // "line","arc"
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
  }
}
