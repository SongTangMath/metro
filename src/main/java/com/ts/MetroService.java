package com.ts;

import com.ts.MetroInfo.MetroLine;
import com.ts.MetroInfo.MetroStation;
import com.ts.MetroInfo.SingleLineMetroInfo;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MetroService {

  public MetroInfo metroInfo(MetroInfoRequest request) {
    switch (CityEnum.fromId(request.getCityId())) {
      case BEIJING:
        return beijingMetroInfo();
      default:
        return null;
    }
  }

  public MetroInfo beijingMetroInfo() {
    // https://map.bjsubway.com/

    List<MetroLine> lines =
        Arrays.asList(
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineName("1号线八通线")
                .stationNames(Arrays.asList())
                .color("#c23a30")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineName("13号线")
                .stationNames(Arrays.asList())
                .color("#f9e700")
                .build());

    List<MetroStation> line1Stations =
        Arrays.asList(
            MetroStation.builder().stationName("苹果园").lineIds(Arrays.asList(1, 6, 24)).build(),
            MetroStation.builder().stationName("古城").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("八角游乐园").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("八宝山").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("玉泉路").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("五棵松").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("万寿路").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("公主坟").lineIds(Arrays.asList(1, 10)).build(),
            MetroStation.builder().stationName("军事博物馆").lineIds(Arrays.asList(1, 9)).build(),
            MetroStation.builder().stationName("木樨地").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("南礼士路").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("复兴门").lineIds(Arrays.asList(1, 2)).build(),
            MetroStation.builder().stationName("西单").lineIds(Arrays.asList(1, 4)).build(),
            MetroStation.builder().stationName("天安门西").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("天安门东").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("王府井").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("东单").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("建国门").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("永安里").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("国贸").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("大望路").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("四惠").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("四惠东").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("高碑店").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("传媒大学").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("双桥").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("管庄").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("八里桥").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("通州北苑").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("果园").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("九棵树").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("梨园").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("临河里").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("土桥").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("花庄").lineIds(Arrays.asList(1, 7)).build(),
            MetroStation.builder().stationName("环球度假区").lineIds(Arrays.asList(1, 7)).build());

    // 东直门与50首都机场线换乘
    List<MetroStation> line2Stations =
        Arrays.asList(
            MetroStation.builder().stationName("西直门").lineIds(Arrays.asList(2, 4, 13)).build(),
            MetroStation.builder().stationName("积水潭").lineIds(Arrays.asList(2, 19)).build(),
            MetroStation.builder().stationName("鼓楼大街").lineIds(Arrays.asList(2, 8)).build(),
            MetroStation.builder().stationName("安定门").lineIds(Arrays.asList(2)).build(),
            MetroStation.builder().stationName("雍和宫").lineIds(Arrays.asList(2, 5)).build(),
            MetroStation.builder().stationName("东直门").lineIds(Arrays.asList(2, 50)).build(),
            MetroStation.builder().stationName("东四十条").lineIds(Arrays.asList(2)).build(),
            MetroStation.builder().stationName("朝阳门").lineIds(Arrays.asList(2, 6)).build(),
            // MetroStation.builder().stationName("建国门").lineIds(Arrays.asList(1,2)).build(),
            MetroStation.builder().stationName("北京站").lineIds(Arrays.asList(2)).build(),
            MetroStation.builder().stationName("崇文门").lineIds(Arrays.asList(2, 5)).build(),
            MetroStation.builder().stationName("前门").lineIds(Arrays.asList(2, 8)).build(),
            MetroStation.builder().stationName("和平门").lineIds(Arrays.asList(2)).build(),
            MetroStation.builder().stationName("宣武门").lineIds(Arrays.asList(2, 4)).build(),
            MetroStation.builder().stationName("长椿街").lineIds(Arrays.asList(2)).build(),
            // MetroStation.builder().stationName("复兴门").lineIds(Arrays.asList(2)).build(),
            MetroStation.builder().stationName("车公庄").lineIds(Arrays.asList(2, 6)).build(),
            MetroStation.builder().stationName("阜成门").lineIds(Arrays.asList(2)).build());

    List<MetroStation> line13Stations =
        Arrays.asList(
            // MetroStation.builder().stationName("西直门").lineIds(Arrays.asList(2, 4, 13)).build(),
            MetroStation.builder().stationName("大钟寺").lineIds(Arrays.asList(13)).build(),
            MetroStation.builder().stationName("知春路").lineIds(Arrays.asList(10, 13)).build(),
            MetroStation.builder().stationName("五道口").lineIds(Arrays.asList(13)).build(),
            MetroStation.builder().stationName("上地").lineIds(Arrays.asList(13)).build(),
            MetroStation.builder().stationName("清河站").lineIds(Arrays.asList(13, 27)).build(),
            MetroStation.builder().stationName("西二旗").lineIds(Arrays.asList(13, 27)).build(),
            MetroStation.builder().stationName("龙泽").lineIds(Arrays.asList(13)).build(),
            MetroStation.builder().stationName("回龙观").lineIds(Arrays.asList(13)).build(),
            MetroStation.builder().stationName("霍营").lineIds(Arrays.asList(8, 13)).build(),
            MetroStation.builder().stationName("立水桥").lineIds(Arrays.asList(5, 13)).build(),
            MetroStation.builder().stationName("北苑").lineIds(Arrays.asList(13)).build(),
            MetroStation.builder().stationName("望京西").lineIds(Arrays.asList(13, 15)).build(),
            MetroStation.builder().stationName("芍药居").lineIds(Arrays.asList(10, 13)).build(),
            MetroStation.builder().stationName("光熙门").lineIds(Arrays.asList(13)).build(),
            MetroStation.builder().stationName("柳芳").lineIds(Arrays.asList(13)).build()

            // ,MetroStation.builder().stationName("东直门").lineIds(Arrays.asList(2, 13)).build()
            );

    List<MetroStation> stations =
        Stream.of(line1Stations, line2Stations, line13Stations)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

    Map<String, MetroStation> stationNameToStation =
        stations
            .stream()
            .collect(Collectors.toMap(MetroStation::getStationName, Function.identity()));

    return MetroInfo.builder()
        .cityId(CityEnum.BEIJING.getId())
        .cityName(CityEnum.BEIJING.getName())
        .lines(lines)
        .stations(stations)
        .stationNameToStation(stationNameToStation)
        .build();
  }

  public SingleLineMetroInfo singleLineMetroInfo(MetroInfoRequest request) {
    MetroInfo allCityMetroInfo = null;
    switch (CityEnum.fromId(request.getCityId())) {
      case BEIJING:
        allCityMetroInfo = beijingMetroInfo();
        break;
      default:
        return null;
    }
    MetroLine line =
        allCityMetroInfo
            .getLines()
            .stream()
            .filter(o -> o.getLineId().equals(request.getLineId()))
            .collect(Collectors.toList())
            .get(0);

    Map<String, MetroStation> stationNameToStation =
        allCityMetroInfo
            .getStations()
            .stream()
            .filter(o -> o.getLineIds().contains(request.getLineId()))
            .collect(Collectors.toMap(MetroStation::getStationName, Function.identity()));

    List<MetroStation> stations =
        line.getStationNames().stream().map(stationNameToStation::get).collect(Collectors.toList());

    return SingleLineMetroInfo.builder()
        .lineId(line.getLineId())
        .lineName(line.getLineName())
        .color(line.getColor())
        .stations(stations)
        .build();
  }
}
