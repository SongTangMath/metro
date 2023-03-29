package com.ts;

import com.ts.MetroInfo.MetroLine;
import com.ts.MetroInfo.MetroStation;
import com.ts.MetroInfo.MetroStationConnection;
import com.ts.MetroInfo.MetroStationConnectionLine;
import com.ts.MetroInfo.MetroTransferLine;
import com.ts.MetroInfo.SingleLineMetroInfo;
import java.util.ArrayList;
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
                .lineId(1)
                .lineName("1号线八通线")
                .stationNames(
                    Arrays.asList(
                        "苹果园", "古城", "八角游乐园", "八宝山", "玉泉路", "五棵松", "万寿路", "公主坟", "军事博物馆", "木樨地",
                        "南礼士路", "复兴门", "西单", "天安门西", "天安门东", "王府井", "东单", "建国门", "永安里", "国贸", "大望路",
                        "四惠", "四惠东", "高碑店", "传媒大学", "双桥", "管庄", "八里桥", "通州北苑", "果园", "九棵树", "梨园",
                        "临河里", "土桥", "花庄", "环球度假区"))
                .color("#c23a30")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(2)
                .lineName("2号线")
                .stationNames(
                    Arrays.asList(
                        "西直门", "积水潭", "鼓楼大街", "安定门", "雍和宫", "东直门", "东四十条", "朝阳门", "建国门", "北京站",
                        "崇文门", "前门", "和平门", "宣武门", "长椿街", "复兴门", "车公庄", "阜成门"))
                .color("#006098")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(4)
                .lineName("4号线大兴线")
                .stationNames(
                    Arrays.asList(
                        "安河桥北", "北宫门", "西苑", "圆明园", "北京大学东门", "中关村", "海淀黄庄", "人民大学", "魏公村", "国家图书馆",
                        "动物园", "西直门", "新街口", "平安里", "西四", "灵境胡同", "西单", "宣武门", "菜市口", "陶然亭", "北京南站",
                        "马家堡", "角门西", "公益西桥", "新宫", "西红门", "高米店北", "高米店南", "枣园", "清源路", "黄村西大街",
                        "黄村火车站", "义和庄", "生物医药基地", "天宫院"))
                .color("#008E9C")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(5)
                .lineName("5号线")
                .stationNames(
                    Arrays.asList(
                        "天通苑北", "天通苑", "天通苑南", "立水桥", "立水桥南", "北苑路北", "大屯路东", "惠新西街北口", "惠新西街南口",
                        "和平西桥", "和平里北街", "雍和宫", "北新桥", "张自忠路", "东四", "灯市口", "东单", "崇文门", "磁器口",
                        "天坛东门", "蒲黄榆", "刘家窑", "宋家庄"))
                .color("#A6217F")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(6)
                .lineName("6号线")
                .stationNames(
                    Arrays.asList(
                        "金安桥", "苹果园", "杨庄", "西黄村", "廖公庄", "田村", "海淀五路居", "慈寿寺", "花园桥", "白石桥南",
                        "二里沟", "车公庄西", "车公庄", "平安里", "北海北", "南锣鼓巷", "东四", "朝阳门", "东大桥", "呼家楼",
                        "金台路", "十里铺", "青年路", "褡裢坡", "黄渠", "常营", "草房", "物资学院路", "通州北关", "通运门",
                        "北运河西", "北运河东", "郝家府", "东夏园", "潞城"))
                .color("#D29700")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(7)
                .lineName("7号线")
                .stationNames(Arrays.asList())
                .color("#f6c582")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(8)
                .lineName("8号线")
                .stationNames(Arrays.asList())
                .color("#009B6B")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(9)
                .lineName("9号线")
                .stationNames(Arrays.asList())
                .color("#8FC31F")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(10)
                .lineName("10号线")
                .stationNames(Arrays.asList())
                .color("#009BC0")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(11)
                .lineName("11号线")
                .stationNames(Arrays.asList())
                .color("#ed796b")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(13)
                .lineName("13号线")
                .stationNames(
                    Arrays.asList(
                        "西直门", "大钟寺", "知春路", "五道口", "上地", "清河站", "西二旗", "龙泽", "回龙观", "霍营", "立水桥",
                        "北苑", "望京西", "芍药居", "光熙门", "柳芳", "东直门"))
                .color("#f9e700")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(14)
                .lineName("14号线")
                .stationNames(Arrays.asList())
                .color("#D5A7A1")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(15)
                .lineName("15号线")
                .stationNames(Arrays.asList())
                .color("#5B2C68")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(24)
                .lineName("S1线")
                .stationNames(Arrays.asList())
                .color("#B35A20")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(27)
                .lineName("昌平线")
                .stationNames(Arrays.asList())
                .color("#DE82B2")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(50)
                .lineName("首都机场线")
                .stationNames(Arrays.asList())
                .color("#A29BBB")
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
            MetroStation.builder().stationName("东单").lineIds(Arrays.asList(1, 5)).build(),
            MetroStation.builder().stationName("建国门").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("永安里").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("国贸").lineIds(Arrays.asList(1, 10)).build(),
            MetroStation.builder().stationName("大望路").lineIds(Arrays.asList(1, 14)).build(),
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
            MetroStation.builder().stationName("东直门").lineIds(Arrays.asList(2, 13, 50)).build(),
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

    List<MetroStation> line4Stations =
        Arrays.asList(
            MetroStation.builder().stationName("安河桥北").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("北宫门").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("西苑").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("圆明园").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("北京大学东门").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("中关村").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("海淀黄庄").lineIds(Arrays.asList(4, 10)).build(),
            MetroStation.builder().stationName("人民大学").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("魏公村").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("国家图书馆").lineIds(Arrays.asList(4, 9)).build(),
            MetroStation.builder().stationName("动物园").lineIds(Arrays.asList(4)).build(),
            // MetroStation.builder().stationName("西直门").lineIds(Arrays.asList(2, 8)).build(),
            MetroStation.builder().stationName("新街口").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("平安里").lineIds(Arrays.asList(4, 6, 19)).build(),
            MetroStation.builder().stationName("西四").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("灵境胡同").lineIds(Arrays.asList(4)).build(),
            // MetroStation.builder().stationName("西单").lineIds(Arrays.asList(1,4)).build(),
            // MetroStation.builder().stationName("宣武门").lineIds(Arrays.asList(2,4)).build(),
            MetroStation.builder().stationName("菜市口").lineIds(Arrays.asList(4, 7)).build(),
            MetroStation.builder().stationName("陶然亭").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("北京南站").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("马家堡").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("角门西").lineIds(Arrays.asList(4, 10)).build(),
            MetroStation.builder().stationName("公益西桥").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("新宫").lineIds(Arrays.asList(4, 19)).build(),
            MetroStation.builder().stationName("西红门").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("高米店北").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("高米店南").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("枣园").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("清源路").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("黄村西大街").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("黄村火车站").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("义和庄").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("生物医药基地").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("天宫院").lineIds(Arrays.asList(4)).build());

    List<MetroStation> line5Stations =
        Arrays.asList(
            MetroStation.builder().stationName("天通苑北").lineIds(Arrays.asList(5)).build(),
            MetroStation.builder().stationName("天通苑").lineIds(Arrays.asList(5)).build(),
            MetroStation.builder().stationName("天通苑南").lineIds(Arrays.asList(5)).build(),
            MetroStation.builder().stationName("立水桥").lineIds(Arrays.asList(5, 13)).build(),
            MetroStation.builder().stationName("立水桥南").lineIds(Arrays.asList(5)).build(),
            MetroStation.builder().stationName("北苑路北").lineIds(Arrays.asList(5)).build(),
            MetroStation.builder().stationName("大屯路东").lineIds(Arrays.asList(5, 15)).build(),
            MetroStation.builder().stationName("惠新西街北口").lineIds(Arrays.asList(5)).build(),
            MetroStation.builder().stationName("惠新西街南口").lineIds(Arrays.asList(5, 10)).build(),
            MetroStation.builder().stationName("和平西桥").lineIds(Arrays.asList(5)).build(),
            MetroStation.builder().stationName("和平里北街").lineIds(Arrays.asList(5, 13)).build(),
            // MetroStation.builder().stationName("雍和宫").lineIds(Arrays.asList(5)).build(),
            MetroStation.builder().stationName("北新桥").lineIds(Arrays.asList(5, 50)).build(),
            MetroStation.builder().stationName("张自忠路").lineIds(Arrays.asList(5)).build(),
            MetroStation.builder().stationName("东四").lineIds(Arrays.asList(5, 6)).build(),
            MetroStation.builder().stationName("灯市口").lineIds(Arrays.asList(5)).build(),
            // MetroStation.builder().stationName("东单").lineIds(Arrays.asList(1, 5)).build(),
            // MetroStation.builder().stationName("崇文门").lineIds(Arrays.asList(2, 5)).build(),
            MetroStation.builder().stationName("磁器口").lineIds(Arrays.asList(5, 7)).build(),
            MetroStation.builder().stationName("天坛东门").lineIds(Arrays.asList(5)).build(),
            MetroStation.builder().stationName("蒲黄榆").lineIds(Arrays.asList(1, 5)).build(),
            MetroStation.builder().stationName("刘家窑").lineIds(Arrays.asList(2, 5)).build(),
            MetroStation.builder().stationName("宋家庄").lineIds(Arrays.asList(5, 10, 26)).build());

    List<MetroStation> line6Stations =
        Arrays.asList(
            MetroStation.builder().stationName("金安桥").lineIds(Arrays.asList(6)).build(),
            // MetroStation.builder().stationName("苹果园").lineIds(Arrays.asList(1, 6)).build(),
            MetroStation.builder().stationName("杨庄").lineIds(Arrays.asList(6)).build(),
            MetroStation.builder().stationName("西黄村").lineIds(Arrays.asList(6)).build(),
            MetroStation.builder().stationName("廖公庄").lineIds(Arrays.asList(6)).build(),
            MetroStation.builder().stationName("田村").lineIds(Arrays.asList(6)).build(),
            MetroStation.builder().stationName("海淀五路居").lineIds(Arrays.asList(6)).build(),
            MetroStation.builder().stationName("慈寿寺").lineIds(Arrays.asList(6, 10)).build(),
            MetroStation.builder().stationName("花园桥").lineIds(Arrays.asList(6)).build(),
            MetroStation.builder().stationName("白石桥南").lineIds(Arrays.asList(6, 9)).build(),
            MetroStation.builder().stationName("二里沟").lineIds(Arrays.asList(6, 16)).build(),
            MetroStation.builder().stationName("车公庄西").lineIds(Arrays.asList(6)).build(),
            // MetroStation.builder().stationName("车公庄").lineIds(Arrays.asList(2, 6)).build(),
            // MetroStation.builder().stationName("平安里").lineIds(Arrays.asList(4, 6)).build(),
            MetroStation.builder().stationName("北海北").lineIds(Arrays.asList(6)).build(),
            MetroStation.builder().stationName("南锣鼓巷").lineIds(Arrays.asList(6, 8)).build(),
            // MetroStation.builder().stationName("东四").lineIds(Arrays.asList(5, 6)).build(),
            // MetroStation.builder().stationName("朝阳门").lineIds(Arrays.asList(2, 6)).build(),
            MetroStation.builder().stationName("东大桥").lineIds(Arrays.asList(6)).build(),
            MetroStation.builder().stationName("呼家楼").lineIds(Arrays.asList(6, 10)).build(),
            MetroStation.builder().stationName("金台路").lineIds(Arrays.asList(6, 14)).build(),
            MetroStation.builder().stationName("十里铺").lineIds(Arrays.asList(6)).build(),
            MetroStation.builder().stationName("青年路").lineIds(Arrays.asList(6)).build(),
            MetroStation.builder().stationName("褡裢坡").lineIds(Arrays.asList(6)).build(),
            MetroStation.builder().stationName("黄渠").lineIds(Arrays.asList(6)).build(),
            MetroStation.builder().stationName("常营").lineIds(Arrays.asList(6)).build(),
            MetroStation.builder().stationName("草房").lineIds(Arrays.asList(6)).build(),
            MetroStation.builder().stationName("物资学院路").lineIds(Arrays.asList(6)).build(),
            MetroStation.builder().stationName("通州北关").lineIds(Arrays.asList(6)).build(),
            MetroStation.builder().stationName("通运门").lineIds(Arrays.asList(6)).build(),
            MetroStation.builder().stationName("北运河西").lineIds(Arrays.asList(6)).build(),
            MetroStation.builder().stationName("北运河东").lineIds(Arrays.asList(6)).build(),
            MetroStation.builder().stationName("郝家府").lineIds(Arrays.asList(6)).build(),
            MetroStation.builder().stationName("东夏园").lineIds(Arrays.asList(6)).build(),
            MetroStation.builder().stationName("潞城").lineIds(Arrays.asList(6)).build());

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
            // MetroStation.builder().stationName("立水桥").lineIds(Arrays.asList(5, 13)).build(),
            MetroStation.builder().stationName("北苑").lineIds(Arrays.asList(13)).build(),
            MetroStation.builder().stationName("望京西").lineIds(Arrays.asList(13, 15)).build(),
            MetroStation.builder().stationName("芍药居").lineIds(Arrays.asList(10, 13)).build(),
            MetroStation.builder().stationName("光熙门").lineIds(Arrays.asList(13)).build(),
            MetroStation.builder().stationName("柳芳").lineIds(Arrays.asList(13)).build()
            // ,MetroStation.builder().stationName("东直门").lineIds(Arrays.asList(2, 13)).build()
            );
    System.out.println(
        line13Stations
            .stream()
            .map(o -> String.format("\"%s\"", o.getStationName()))
            .collect(Collectors.joining(",")));

    List<MetroStation> stations =
        Stream.of(
                line1Stations,
                line2Stations,
                line4Stations,
                line5Stations,
                line6Stations,
                line13Stations)
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
            .filter(o -> o.getLineName().equals(request.getLineName()))
            .collect(Collectors.toList())
            .get(0);

    Map<String, MetroStation> stationNameToStation =
        allCityMetroInfo
            .getStations()
            .stream()
            .filter(o -> o.getLineIds().contains(line.getLineId()))
            .collect(Collectors.toMap(MetroStation::getStationName, Function.identity()));

    List<MetroStation> stations =
        line.getStationNames().stream().map(stationNameToStation::get).collect(Collectors.toList());

    Map<Integer, MetroLine> lineIdToLine =
        allCityMetroInfo
            .getLines()
            .stream()
            .collect(Collectors.toMap(MetroLine::getLineId, Function.identity()));

    // 计算单线路绘图的坐标信息.对于非环线,线路图为直线.站点的y坐标为canvas.height/2 x轴分成(站点个数+1)份.
    for (int i = 0; i < stations.size(); i++) {
      stations.get(i).setXPos((i + 1) * request.getCanvasWidth() / (stations.size() + 1.0));
      stations.get(i).setYPos(request.getCanvasHeight() / 2.0);
    }
    // 计算站点间连线数据.对于非环线,n个站点需要n-1个"线段"连接.每个线段为矩形,左右两边为凹进来的半圆. canvas绘图表现为arc+line+arc+line
    // 注意canvas绘图中原点是左上角且y轴方向向下
    List<MetroStationConnection> connections = new ArrayList<>();

    for (int i = 0; i < stations.size() - 1; i++) {

      // 左站点的右弧.逆时针 圆心为左站点中心.半径从请求中传入

      Double theta = Math.asin(request.getConnectionLineWidth() / 2.0 / request.getStationRadius());
      MetroStationConnectionLine leftStationRightArc =
          MetroStationConnectionLine.builder()
              .lineType(MetroStationLineTypeEnum.ARC.getIdentifier())
              .arcCenterX(stations.get(i).getXPos())
              .arcCenterY(stations.get(i).getYPos())
              .radius(request.getStationRadius())
              .arcStartAngle(theta)
              .arcEndAngle(-theta)
              .isCounterClockWise(true)
              .build();
      // 挖掉半圆的矩形的顶部线段
      MetroStationConnectionLine topLine =
          MetroStationConnectionLine.builder()
              .lineType(MetroStationLineTypeEnum.LINE.getIdentifier())
              .lineStartX(stations.get(i).getXPos() + request.getStationRadius() * Math.cos(theta))
              .lineStartY(stations.get(i).getYPos() - request.getStationRadius() * Math.sin(theta))
              .lineEndX(
                  stations.get(i + 1).getXPos() - request.getStationRadius() * Math.cos(theta))
              .lineEndY(
                  stations.get(i + 1).getYPos() - request.getStationRadius() * Math.sin(theta))
              .build();

      MetroStationConnectionLine rightStationLeftArc =
          MetroStationConnectionLine.builder()
              .lineType(MetroStationLineTypeEnum.ARC.getIdentifier())
              .arcCenterX(stations.get(i + 1).getXPos())
              .arcCenterY(stations.get(i + 1).getYPos())
              .radius(request.getStationRadius())
              .arcStartAngle(Math.PI + theta)
              .arcEndAngle(Math.PI - theta)
              .isCounterClockWise(true)
              .build();

      MetroStationConnectionLine bottomLine =
          MetroStationConnectionLine.builder()
              .lineType(MetroStationLineTypeEnum.LINE.getIdentifier())
              .lineStartX(
                  stations.get(i + 1).getXPos() - request.getStationRadius() * Math.cos(theta))
              .lineStartY(
                  stations.get(i + 1).getYPos() + request.getStationRadius() * Math.sin(theta))
              .lineEndX(stations.get(i).getXPos() + request.getStationRadius() * Math.cos(theta))
              .lineEndY(stations.get(i).getYPos() + request.getStationRadius() * Math.sin(theta))
              .build();

      connections.add(
          MetroStationConnection.builder()
              .lines(Arrays.asList(leftStationRightArc, topLine, rightStationLeftArc, bottomLine))
              .color(line.getColor())
              .build());
    }

    // 拼接换乘站相关信息 注意在此之前已经计算了各站点的绘图位置
    List<MetroStationConnection> stationTransfers = new ArrayList<>();
    List<MetroInfo.MetroStationTransferText> transferTexts = new ArrayList<>();
    for (MetroStation stationWithTransfer :
        stations.stream().filter(o -> o.getLineIds().size() > 1).collect(Collectors.toList())) {

      List<Integer> otherLineIds =
          stationWithTransfer
              .getLineIds()
              .stream()
              .filter(o -> !line.getLineId().equals(o))
              .collect(Collectors.toList());

      stationWithTransfer.setTransferLines(
          otherLineIds
              .stream()
              .map(lineIdToLine::get)
              .map(
                  o ->
                      MetroTransferLine.builder()
                          .lineId(o.getLineId())
                          .lineName(o.getLineName())
                          .color(o.getColor())
                          .build())
              .collect(Collectors.toList()));
      // 暂时不考虑4线换乘的情况.即至多有2条换乘线路

      Point point1 =
          new Point(
              stationWithTransfer.getXPos() - request.getStationRadius(),
              stationWithTransfer.getYPos());
      Point point2 =
          new Point(
              stationWithTransfer.getXPos() - request.getStationRadius(),
              stationWithTransfer.getYPos() + request.getCanvasHeight() / 15);

      Point point3 =
          new Point(
              stationWithTransfer.getXPos(),
              stationWithTransfer.getYPos()
                  + request.getCanvasHeight() / 15
                  + request.getStationRadius());
      Point point4 =
          new Point(
              stationWithTransfer.getXPos() + request.getStationRadius(),
              stationWithTransfer.getYPos() + request.getCanvasHeight() / 15);
      Point point5 =
          new Point(
              stationWithTransfer.getXPos() + request.getStationRadius(),
              stationWithTransfer.getYPos());

      Point point6 =
          new Point(
              stationWithTransfer.getXPos(),
              stationWithTransfer.getYPos() + request.getStationRadius());

      switch (otherLineIds.size()) {
        case 1:
          {
            // 换乘站标记位于站点圆盘的下方.

            // 顶部半圆
            MetroStationConnectionLine topArc =
                MetroStationConnectionLine.builder()
                    .lineType(MetroStationLineTypeEnum.ARC.getIdentifier())
                    .arcCenterX(stationWithTransfer.getXPos())
                    .arcCenterY(stationWithTransfer.getYPos())
                    .radius(request.getStationRadius())
                    .arcStartAngle(0.0)
                    .arcEndAngle(Math.PI)
                    .build();

            MetroStationConnectionLine leftLine =
                MetroStationConnectionLine.constructFromPoints(point1, point2);

            MetroStationConnectionLine bottomLeftLine =
                MetroStationConnectionLine.constructFromPoints(point2, point3);

            MetroStationConnectionLine bottomRightLine =
                MetroStationConnectionLine.constructFromPoints(point3, point4);

            MetroStationConnectionLine rightLine =
                MetroStationConnectionLine.constructFromPoints(point4, point5);

            stationTransfers.add(
                MetroStationConnection.builder()
                    .lines(
                        Arrays.asList(topArc, leftLine, bottomLeftLine, bottomRightLine, rightLine))
                    .color(lineIdToLine.get(otherLineIds.get(0)).getColor())
                    .build());
          }

          break;
        case 2:
          {
            // 第0换乘线顶部半圆
            MetroStationConnectionLine transferLine0TopArc =
                MetroStationConnectionLine.builder()
                    .lineType(MetroStationLineTypeEnum.ARC.getIdentifier())
                    .arcCenterX(stationWithTransfer.getXPos())
                    .arcCenterY(stationWithTransfer.getYPos())
                    .radius(request.getStationRadius())
                    .arcStartAngle(Math.PI / 2)
                    .arcEndAngle(Math.PI)
                    .build();

            MetroStationConnectionLine transferLine1TopArc =
                MetroStationConnectionLine.builder()
                    .lineType(MetroStationLineTypeEnum.ARC.getIdentifier())
                    .arcCenterX(stationWithTransfer.getXPos())
                    .arcCenterY(stationWithTransfer.getYPos())
                    .radius(request.getStationRadius())
                    .arcStartAngle(0.0)
                    .arcEndAngle(Math.PI / 2)
                    .build();

            stationTransfers.add(
                MetroStationConnection.builder()
                    .lines(
                        Arrays.asList(
                            transferLine0TopArc,
                            MetroStationConnectionLine.constructFromPoints(point1, point2),
                            MetroStationConnectionLine.constructFromPoints(point2, point3),
                            MetroStationConnectionLine.constructFromPoints(point3, point6)))
                    .color(lineIdToLine.get(otherLineIds.get(0)).getColor())
                    .build());

            stationTransfers.add(
                MetroStationConnection.builder()
                    .lines(
                        Arrays.asList(
                            transferLine1TopArc,
                            MetroStationConnectionLine.constructFromPoints(point6, point3),
                            MetroStationConnectionLine.constructFromPoints(point3, point4),
                            MetroStationConnectionLine.constructFromPoints(point4, point5)))
                    .color(lineIdToLine.get(otherLineIds.get(1)).getColor())
                    .build());
          }
          break;
        default:
          break;
      }

      // 在point3下方10位置处添加文本
      for (int i = 0; i < otherLineIds.size(); i++) {
        transferTexts.add(
            MetroInfo.MetroStationTransferText.builder()
                .text("换乘" + lineIdToLine.get(otherLineIds.get(i)).getLineName())
                .xPos(stationWithTransfer.getXPos())
                .yPos(point3.getY() + (i + 1) * request.getFontSize())
                .build());
      }
    }

    return SingleLineMetroInfo.builder()
        .lineId(line.getLineId())
        .lineName(line.getLineName())
        .color(line.getColor())
        .stations(stations)
        .stationConnections(connections)
        .stationTransfers(stationTransfers)
        .transferTexts(transferTexts)
        .build();
  }
}
