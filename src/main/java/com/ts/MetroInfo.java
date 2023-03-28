package com.ts;

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
  public static class MetroStation {
    private Integer stationId;
    private String stationName;
    // 经度
    private Double longitude;
    // 维度
    private Double latitude;

    private List<Integer> lineIds;
    // 如果为换乘站,拼接换乘站的信息
    private List<MetroTransferLines> transferLines;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  @Builder
  public static class MetroTransferLines {
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
  }
}
