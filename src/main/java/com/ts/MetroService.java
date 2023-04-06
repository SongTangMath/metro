package com.ts;

import com.ts.constants.CityEnum;
import com.ts.constants.ConnectionTypeEnum;
import com.ts.constants.MetroStationLineTypeEnum;
import com.ts.dto.CityWithMetroResponse;
import com.ts.dto.MetroInfo;
import com.ts.dto.MetroInfo.MetroBranchLine;
import com.ts.dto.MetroInfo.MetroLine;
import com.ts.dto.MetroInfo.MetroStation;
import com.ts.dto.MetroInfo.MetroStationConnection;
import com.ts.dto.MetroInfo.MetroStationConnectionLine;
import com.ts.dto.MetroInfo.MetroTransferLine;
import com.ts.dto.MetroInfo.SingleLineMetroInfo;
import com.ts.dto.MetroInfoRequest;
import com.ts.dto.Point;
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
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class MetroService {

  public CityWithMetroResponse allCityMetroInfo() {

    List<MetroInfo> metroInfos = Arrays.asList(beijingMetroInfo(), guangzhouMetroInfo());
    for (MetroInfo metroInfo : metroInfos) {
      metroInfo.calculateStationCount();
    }
    return new CityWithMetroResponse(metroInfos);
  }

  public MetroInfo metroInfo(MetroInfoRequest request) {
    MetroInfo metroInfo = null;
    switch (CityEnum.fromId(request.getCityId())) {
      case BEIJING:
        metroInfo = beijingMetroInfo();
        break;
      case GUANGZHOU:
        metroInfo = guangzhouMetroInfo();
        break;
      default:
        return null;
    }
    metroInfo.calculateStationCount();
    return metroInfo;
  }

  public MetroInfo beijingMetroInfo() {
    // 线路图 https://map.bjsubway.com/
    // 颜色 https://www.bilibili.com/read/cv19838337/ 这个颜色和上面官网线路图的有些不同,但是似乎更准一些
    // M24(S1线) M25(房山线) M26(亦庄线) M27(昌平线)
    // 首都机场线(id=50),大兴机场线,西郊线

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
                .color("#a4343a")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(2)
                .lineName("2号线")
                .stationNames(
                    Arrays.asList(
                        "西直门", "积水潭", "鼓楼大街", "安定门", "雍和宫", "东直门", "东四十条", "朝阳门", "建国门", "北京站",
                        "崇文门", "前门", "和平门", "宣武门", "长椿街", "复兴门", "车公庄", "阜成门"))
                .color("#004b87")
                .isLoopLine(true)
                .topStationNames(Arrays.asList("积水潭", "鼓楼大街", "安定门", "雍和宫"))
                .rightStationNames(Arrays.asList("东直门", "东四十条", "朝阳门", "建国门"))
                .bottomStationNames(Arrays.asList("北京站", "崇文门", "前门", "和平门", "宣武门"))
                .leftStationNames(Arrays.asList("长椿街", "复兴门", "车公庄", "阜成门", "西直门"))
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
                .color("#008c95")
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
                .color("#aa0061")
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
                .color("#b58500")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(7)
                .lineName("7号线")
                .stationNames(
                    Arrays.asList(
                        "北京西站", "湾子", "达官营", "广安门内", "菜市口", "虎坊桥", "珠市口", "桥湾", "磁器口", "广渠门内",
                        "广渠门外", "双井", "九龙山", "大郊亭", "百子湾", "化工", "南楼梓庄", "欢乐谷景区", "垡头", "双合", "焦化厂",
                        "黄厂", "郎辛庄", "黑庄户", "万盛西", "万盛东", "群芳", "高楼金", "花庄", "环球度假区"))
                .color("#ffc56e")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(8)
                .lineName("8号线")
                .stationNames(
                    Arrays.asList(
                        "朱辛庄", "育知路", "平西府", "回龙观东大街", "霍营", "育新", "西小口", "永泰庄", "林萃桥", "森林公园南门",
                        "奥林匹克公园", "奥体中心", "北土城", "安华桥", "安德里北街", "鼓楼大街", "什刹海", "南锣鼓巷", "中国美术馆",
                        "金鱼胡同", "王府井", "前门", "珠市口", "天桥", "永定门外", "木樨园", "海户屯", "大红门南", "和义", "东高地",
                        "火箭万源", "五福堂", "德茂", "瀛海"))
                .color("#009b77")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(9)
                .lineName("9号线")
                .stationNames(
                    Arrays.asList(
                        "国家图书馆", "白石桥南", "白堆子", "军事博物馆", "北京西站", "六里桥东", "六里桥", "七里庄", "丰台东大街",
                        "凤台南路", "科怡路", "丰台科技园", "郭公庄"))
                .color("#97d700")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(10)
                .lineName("10号线")
                .stationNames(
                    Arrays.asList(
                        "巴沟", "火器营", "长春桥", "车道沟", "慈寿寺", "西钓鱼台", "公主坟", "莲花桥", "六里桥", "西局", "泥洼",
                        "丰台站", "首经贸", "纪家庙", "草桥", "角门西", "角门东", "大红门", "石榴庄", "宋家庄", "成寿寺", "分钟寺",
                        "十里河", "潘家园", "劲松", "双井", "国贸", "金台夕照", "呼家楼", "团结湖", "农业展览馆", "亮马桥", "三元桥",
                        "太阳宫", "芍药居", "惠新西街南口", "安贞门", "北土城", "健德门", "牡丹园", "西土城", "知春路", "知春里",
                        "海淀黄庄", "苏州街"))
                .color("#0092bc")
                .isLoopLine(true)
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(11)
                .lineName("11号线")
                .stationNames(Arrays.asList())
                .color("#ff8674")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(12)
                .lineName("12号线")
                .stationNames(Arrays.asList())
                .color("#9c4f01")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(13)
                .lineName("13号线")
                .stationNames(
                    Arrays.asList(
                        "西直门", "大钟寺", "知春路", "五道口", "上地", "清河站", "西二旗", "龙泽", "回龙观", "霍营", "立水桥",
                        "北苑", "望京西", "芍药居", "光熙门", "柳芳", "东直门"))
                .color("#f4da40")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(14)
                .lineName("14号线")
                .stationNames(Arrays.asList())
                .color("#ca9a8e")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(15)
                .lineName("15号线")
                .stationNames(Arrays.asList())
                .color("#653279")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(16)
                .lineName("16号线")
                .stationNames(Arrays.asList())
                .color("#6ba539")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(17)
                .lineName("17号线")
                .stationNames(Arrays.asList())
                .color("#00abab")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(19)
                .lineName("19号线")
                .stationNames(Arrays.asList())
                .color("#d3a3c9")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(22)
                .lineName("平谷线")
                .stationNames(Arrays.asList())
                .color("#f4c1ca")
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
                .lineId(25)
                .lineName("房山线燕房线")
                .stationNames(Arrays.asList())
                .color("#d86018")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(26)
                .lineName("亦庄线")
                .stationNames(Arrays.asList())
                .color("#d0006f")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(27)
                .lineName("昌平线")
                .stationNames(Arrays.asList())
                .color("#d986ba")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.BEIJING.getId())
                .lineId(50)
                .lineName("首都机场线")
                .stationNames(Arrays.asList())
                .color("#a192b2")
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
            MetroStation.builder().stationName("王府井").lineIds(Arrays.asList(1, 8)).build(),
            MetroStation.builder().stationName("东单").lineIds(Arrays.asList(1, 5)).build(),
            MetroStation.builder().stationName("建国门").lineIds(Arrays.asList(1, 2)).build(),
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

    List<MetroStation> line7Stations =
        Arrays.asList(
            MetroStation.builder().stationName("北京西站").lineIds(Arrays.asList(7, 9)).build(),
            MetroStation.builder().stationName("湾子").lineIds(Arrays.asList(7)).build(),
            MetroStation.builder().stationName("达官营").lineIds(Arrays.asList(7)).build(),
            MetroStation.builder().stationName("广安门内").lineIds(Arrays.asList(7)).build(),

            //  MetroStation.builder().stationName("菜市口").lineIds(Arrays.asList(4,7)).build(),
            MetroStation.builder().stationName("虎坊桥").lineIds(Arrays.asList(7)).build(),
            MetroStation.builder().stationName("珠市口").lineIds(Arrays.asList(7, 8)).build(),
            MetroStation.builder().stationName("桥湾").lineIds(Arrays.asList(7)).build(),
            // MetroStation.builder().stationName("磁器口").lineIds(Arrays.asList(5,7)).build(),
            MetroStation.builder().stationName("广渠门内").lineIds(Arrays.asList(7)).build(),
            MetroStation.builder().stationName("广渠门外").lineIds(Arrays.asList(7)).build(),
            MetroStation.builder().stationName("双井").lineIds(Arrays.asList(7, 10)).build(),
            MetroStation.builder().stationName("九龙山").lineIds(Arrays.asList(7)).build(),
            MetroStation.builder().stationName("大郊亭").lineIds(Arrays.asList(7)).build(),
            MetroStation.builder().stationName("百子湾").lineIds(Arrays.asList(7)).build(),
            MetroStation.builder().stationName("化工").lineIds(Arrays.asList(7)).build(),
            MetroStation.builder().stationName("南楼梓庄").lineIds(Arrays.asList(7)).build(),
            MetroStation.builder().stationName("欢乐谷景区").lineIds(Arrays.asList(7)).build(),
            MetroStation.builder().stationName("垡头").lineIds(Arrays.asList(7)).build(),
            MetroStation.builder().stationName("双合").lineIds(Arrays.asList(7)).build(),
            MetroStation.builder().stationName("焦化厂").lineIds(Arrays.asList(7)).build(),
            MetroStation.builder().stationName("黄厂").lineIds(Arrays.asList(7)).build(),
            MetroStation.builder().stationName("郎辛庄").lineIds(Arrays.asList(7)).build(),
            MetroStation.builder().stationName("黑庄户").lineIds(Arrays.asList(7)).build(),
            MetroStation.builder().stationName("万盛西").lineIds(Arrays.asList(7)).build(),
            MetroStation.builder().stationName("万盛东").lineIds(Arrays.asList(7)).build(),
            MetroStation.builder().stationName("群芳").lineIds(Arrays.asList(7)).build(),
            MetroStation.builder().stationName("高楼金").lineIds(Arrays.asList(7)).build()
            //  ,MetroStation.builder().stationName("花庄").lineIds(Arrays.asList(1,7)).build(),
            //  MetroStation.builder().stationName("环球度假区").lineIds(Arrays.asList(1,7)).build()
            );

    List<MetroStation> line8Stations =
        Arrays.asList(
            MetroStation.builder().stationName("朱辛庄").lineIds(Arrays.asList(8, 27)).build(),
            MetroStation.builder().stationName("育知路").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("平西府").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("回龙观东大街").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("霍营").lineIds(Arrays.asList(8, 13)).build(),
            MetroStation.builder().stationName("育新").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("西小口").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("永泰庄").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("林萃桥").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("森林公园南门").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("奥林匹克公园").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("奥体中心").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("北土城").lineIds(Arrays.asList(8, 10)).build(),
            MetroStation.builder().stationName("安华桥").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("安德里北街").lineIds(Arrays.asList(8)).build(),

            //    MetroStation.builder().stationName("鼓楼大街").lineIds(Arrays.asList(2,8)).build(),
            MetroStation.builder().stationName("什刹海").lineIds(Arrays.asList(8)).build(),
            //   MetroStation.builder().stationName("南锣鼓巷").lineIds(Arrays.asList(6,8)).build(),
            MetroStation.builder().stationName("中国美术馆").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("金鱼胡同").lineIds(Arrays.asList(8)).build(),
            //   MetroStation.builder().stationName("王府井").lineIds(Arrays.asList(1,8)).build(),
            //  MetroStation.builder().stationName("前门").lineIds(Arrays.asList(2,8)).build(),
            //  MetroStation.builder().stationName("珠市口").lineIds(Arrays.asList(7,8)).build(),
            MetroStation.builder().stationName("天桥").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("永定门外").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("木樨园").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("海户屯").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("大红门南").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("和义").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("东高地").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("火箭万源").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("五福堂").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("德茂").lineIds(Arrays.asList(8)).build(),
            MetroStation.builder().stationName("瀛海").lineIds(Arrays.asList(8)).build());

    List<MetroStation> line9Stations =
        Arrays.asList(
            // MetroStation.builder().stationName("国家图书馆").lineIds(Arrays.asList(4,9,16)).build(),
            //   MetroStation.builder().stationName("白石桥南").lineIds(Arrays.asList(6,9)).build(),
            MetroStation.builder().stationName("白堆子").lineIds(Arrays.asList(9)).build(),
            //   MetroStation.builder().stationName("军事博物馆").lineIds(Arrays.asList(1,9)).build(),

            //   MetroStation.builder().stationName("北京西站").lineIds(Arrays.asList(7,9)).build(),
            MetroStation.builder().stationName("六里桥东").lineIds(Arrays.asList(9)).build(),
            MetroStation.builder().stationName("六里桥").lineIds(Arrays.asList(9, 10)).build(),
            MetroStation.builder().stationName("七里庄").lineIds(Arrays.asList(9)).build(),
            MetroStation.builder().stationName("丰台东大街").lineIds(Arrays.asList(9)).build(),
            MetroStation.builder().stationName("凤台南路").lineIds(Arrays.asList(9)).build(),
            MetroStation.builder().stationName("科怡路").lineIds(Arrays.asList(9)).build(),
            MetroStation.builder().stationName("丰台科技园").lineIds(Arrays.asList(9)).build(),
            MetroStation.builder().stationName("郭公庄").lineIds(Arrays.asList(9, 25)).build());

    List<MetroStation> line10Stations =
        Arrays.asList(
            MetroStation.builder().stationName("巴沟").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("火器营").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("长春桥").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("车道沟").lineIds(Arrays.asList(10)).build(),
            //   MetroStation.builder().stationName("慈寿寺").lineIds(Arrays.asList(6,10)).build(),
            MetroStation.builder().stationName("西钓鱼台").lineIds(Arrays.asList(10)).build(),

            //   MetroStation.builder().stationName("公主坟").lineIds(Arrays.asList(1,10)).build(),
            MetroStation.builder().stationName("莲花桥").lineIds(Arrays.asList(10)).build(),
            //   MetroStation.builder().stationName("六里桥").lineIds(Arrays.asList(9,10)).build(),

            MetroStation.builder().stationName("西局").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("泥洼").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("丰台站").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("首经贸").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("纪家庙").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("草桥").lineIds(Arrays.asList(10)).build(),

            //  MetroStation.builder().stationName("角门西").lineIds(Arrays.asList(4,10)).build(),
            MetroStation.builder().stationName("角门东").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("大红门").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("石榴庄").lineIds(Arrays.asList(10)).build(),
            //  MetroStation.builder().stationName("宋家庄").lineIds(Arrays.asList(5,10,26)).build(),
            MetroStation.builder().stationName("成寿寺").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("分钟寺").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("十里河").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("潘家园").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("劲松").lineIds(Arrays.asList(10)).build(),
            //    MetroStation.builder().stationName("双井").lineIds(Arrays.asList(7,10)).build(),
            //      MetroStation.builder().stationName("国贸").lineIds(Arrays.asList(1,10)).build(),

            MetroStation.builder().stationName("金台夕照").lineIds(Arrays.asList(10)).build(),
            //    MetroStation.builder().stationName("呼家楼").lineIds(Arrays.asList(6,10)).build(),
            MetroStation.builder().stationName("团结湖").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("农业展览馆").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("亮马桥").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("三元桥").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("太阳宫").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("芍药居").lineIds(Arrays.asList(10, 13)).build(),
            //    MetroStation.builder().stationName("惠新西街南口").lineIds(Arrays.asList(5,10)).build(),

            MetroStation.builder().stationName("安贞门").lineIds(Arrays.asList(10)).build(),
            //   MetroStation.builder().stationName("北土城").lineIds(Arrays.asList(8,10)).build(),
            MetroStation.builder().stationName("健德门").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("牡丹园").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("西土城").lineIds(Arrays.asList(10)).build(),
            MetroStation.builder().stationName("知春路").lineIds(Arrays.asList(10, 13)).build(),
            MetroStation.builder().stationName("知春里").lineIds(Arrays.asList(10)).build(),
            //   MetroStation.builder().stationName("海淀黄庄").lineIds(Arrays.asList(4,10)).build(),
            MetroStation.builder().stationName("苏州街").lineIds(Arrays.asList(10)).build());

    System.out.println(
        line10Stations
            .stream()
            .map(o -> String.format("\"%s\"", o.getStationName()))
            .collect(Collectors.joining(",")));

    List<MetroStation> line13Stations =
        Arrays.asList(
            // MetroStation.builder().stationName("西直门").lineIds(Arrays.asList(2, 4, 13)).build(),
            MetroStation.builder().stationName("大钟寺").lineIds(Arrays.asList(13)).build(),
            //    MetroStation.builder().stationName("知春路").lineIds(Arrays.asList(10, 13)).build(),
            MetroStation.builder().stationName("五道口").lineIds(Arrays.asList(13)).build(),
            MetroStation.builder().stationName("上地").lineIds(Arrays.asList(13)).build(),
            MetroStation.builder().stationName("清河站").lineIds(Arrays.asList(13, 27)).build(),
            MetroStation.builder().stationName("西二旗").lineIds(Arrays.asList(13, 27)).build(),
            MetroStation.builder().stationName("龙泽").lineIds(Arrays.asList(13)).build(),
            MetroStation.builder().stationName("回龙观").lineIds(Arrays.asList(13)).build(),
            //  MetroStation.builder().stationName("霍营").lineIds(Arrays.asList(8, 13)).build(),
            // MetroStation.builder().stationName("立水桥").lineIds(Arrays.asList(5, 13)).build(),
            MetroStation.builder().stationName("北苑").lineIds(Arrays.asList(13)).build(),
            MetroStation.builder().stationName("望京西").lineIds(Arrays.asList(13, 15)).build(),
            //  MetroStation.builder().stationName("芍药居").lineIds(Arrays.asList(10, 13)).build(),
            MetroStation.builder().stationName("光熙门").lineIds(Arrays.asList(13)).build(),
            MetroStation.builder().stationName("柳芳").lineIds(Arrays.asList(13)).build()
            // ,MetroStation.builder().stationName("东直门").lineIds(Arrays.asList(2, 13)).build()
            );

    List<MetroStation> stations =
        Stream.of(
                line1Stations,
                line2Stations,
                line4Stations,
                line5Stations,
                line6Stations,
                line7Stations,
                line8Stations,
                line9Stations,
                line10Stations,
                line13Stations)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

    stations.stream().collect(Collectors.toMap(MetroStation::getStationName, Function.identity()));

    return MetroInfo.builder()
        .cityId(CityEnum.BEIJING.getId())
        .cityName(CityEnum.BEIJING.getName())
        .lines(lines)
        .stations(stations)
        .build();
  }

  public MetroInfo guangzhouMetroInfo() {
    // https://cs.gzmtr.com/ckfw/xlu_2020/202011/W020221227789185419694.png
    // 线路颜色 https://www.bilibili.com/read/cv13783696/
    // 佛山地铁线路颜色 https://www.bilibili.com/read/cv19915012/
    // apm线(id=23) 海珠有轨1号线(24) 黄埔有轨1号线(25)
    // 广佛线(26) 佛山2号线(27) 佛山3号线(28)

    List<MetroLine> lines =
        Arrays.asList(
            MetroLine.builder()
                .cityId(CityEnum.GUANGZHOU.getId())
                .lineId(1)
                .lineName("1号线")
                .stationNames(
                    Arrays.asList(
                        "西塱", "坑口", "花地湾", "芳村", "黄沙", "长寿路", "陈家祠", "西门口", "公园前", "农讲所", "东山口",
                        "杨箕", "体育西路", "体育中心", "广州东站"))
                .color("#f3d03e")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.GUANGZHOU.getId())
                .lineId(2)
                .lineName("2号线")
                .stationNames(
                    Arrays.asList(
                        "广州南站", "石壁", "会江", "南浦", "洛溪", "南洲", "东晓南", "江泰路", "昌岗", "江南西", "市二宫",
                        "海珠广场", "公园前", "纪念堂", "越秀公园", "广州火车站", "三元里", "飞翔公园", "白云公园", "白云文化广场",
                        "萧岗", "江夏", "黄边", "嘉禾望岗"))
                .color("#00629b")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.GUANGZHOU.getId())
                .lineId(3)
                .lineName("3号线")
                .stationNames(
                    Arrays.asList(
                        "番禺广场", "市桥", "汉溪长隆", "大石", "厦滘", "沥滘", "大塘", "客村", "广州塔", "珠江新城", "体育西路",
                        "石牌桥", "岗顶", "华师", "五山", "天河客运站"))
                .branchLines(
                    Arrays.asList(
                        MetroBranchLine.builder()
                            .branchLineName("3号线北延线")
                            .branchLineStationNames(
                                Arrays.asList(
                                    "体育西路", "林和西", "广州东站", "燕塘", "梅花园", "京溪南方医院", "同和", "永泰",
                                    "白云大道北", "嘉禾望岗", "龙归", "人和", "高增", "机场南", "机场北"))
                            .build()))
                .color("#eca154")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.GUANGZHOU.getId())
                .lineId(4)
                .lineName("4号线")
                .stationNames(
                    Arrays.asList(
                        "南沙客运港", "南横", "塘坑", "大涌", "广隆", "飞沙角", "金洲", "蕉门", "黄阁", "黄阁汽车城", "庆盛",
                        "东涌", "低涌", "海傍", "石碁", "官桥", "新造", "大学城南", "官洲", "万胜围", "车陂南", "车陂", "黄村"))
                .color("#00843d")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.GUANGZHOU.getId())
                .lineId(5)
                .lineName("5号线")
                .stationNames(Arrays.asList())
                .color("#c5003e")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.GUANGZHOU.getId())
                .lineId(6)
                .lineName("6号线")
                .stationNames(Arrays.asList())
                .color("#80225f")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.GUANGZHOU.getId())
                .lineId(7)
                .lineName("7号线")
                .stationNames(Arrays.asList())
                .color("#97d700")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.GUANGZHOU.getId())
                .lineId(8)
                .lineName("8号线")
                .stationNames(Arrays.asList())
                .color("#008c95")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.GUANGZHOU.getId())
                .lineId(9)
                .lineName("9号线")
                .stationNames(Arrays.asList())
                .color("#71cc98")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.GUANGZHOU.getId())
                .lineId(13)
                .lineName("13号线")
                .stationNames(Arrays.asList())
                .color("#8e8c13")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.GUANGZHOU.getId())
                .lineId(14)
                .lineName("14号线")
                .stationNames(Arrays.asList())
                .color("#81312f")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.GUANGZHOU.getId())
                .lineId(18)
                .lineName("18号线")
                .stationNames(Arrays.asList())
                .color("#0047ba")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.GUANGZHOU.getId())
                .lineId(21)
                .lineName("21号线")
                .stationNames(Arrays.asList())
                .color("#201747")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.GUANGZHOU.getId())
                .lineId(22)
                .lineName("22号线")
                .stationNames(Arrays.asList())
                .color("#cd5228")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.GUANGZHOU.getId())
                .lineId(23)
                .lineName("apm线")
                .stationNames(Arrays.asList())
                .color("#00b5e2")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.GUANGZHOU.getId())
                .lineId(24)
                .lineName("有轨电车THZ1线")
                .stationNames(Arrays.asList())
                .color("#43b02a")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.GUANGZHOU.getId())
                .lineId(25)
                .lineName("黄埔有轨电车1号线")
                .stationNames(Arrays.asList())
                .color("#d42d1b")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.GUANGZHOU.getId())
                .lineId(26)
                .lineName("广佛线")
                .stationNames(Arrays.asList())
                .color("#c4d600")
                .build(),
            MetroLine.builder()
                .cityId(CityEnum.GUANGZHOU.getId())
                .lineId(27)
                .lineName("佛山2号线")
                .stationNames(Arrays.asList())
                .color("#f5333f")
                .build());

    List<MetroStation> line1Stations =
        Arrays.asList(
            MetroStation.builder().stationName("西塱").lineIds(Arrays.asList(1, 26)).build(),
            MetroStation.builder().stationName("坑口").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("花地湾").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("芳村").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("黄沙").lineIds(Arrays.asList(1, 6)).build(),
            MetroStation.builder().stationName("长寿路").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("陈家祠").lineIds(Arrays.asList(1, 8)).build(),
            MetroStation.builder().stationName("西门口").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("公园前").lineIds(Arrays.asList(1, 2)).build(),
            MetroStation.builder().stationName("农讲所").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("东山口").lineIds(Arrays.asList(1, 6)).build(),
            MetroStation.builder().stationName("杨箕").lineIds(Arrays.asList(1, 5)).build(),
            MetroStation.builder().stationName("体育西路").lineIds(Arrays.asList(1, 3)).build(),
            MetroStation.builder().stationName("体育中心").lineIds(Arrays.asList(1)).build(),
            MetroStation.builder().stationName("广州东站").lineIds(Arrays.asList(1, 3)).build());

    List<MetroStation> line2Stations =
        Arrays.asList(
            MetroStation.builder().stationName("广州南站").lineIds(Arrays.asList(2, 22, 7, 27)).build(),
            MetroStation.builder().stationName("石壁").lineIds(Arrays.asList(2)).build(),
            MetroStation.builder().stationName("会江").lineIds(Arrays.asList(2)).build(),
            MetroStation.builder().stationName("南浦").lineIds(Arrays.asList(2)).build(),
            MetroStation.builder().stationName("洛溪").lineIds(Arrays.asList(2)).build(),
            MetroStation.builder().stationName("南洲").lineIds(Arrays.asList(2, 26)).build(),
            MetroStation.builder().stationName("东晓南").lineIds(Arrays.asList(2)).build(),
            MetroStation.builder().stationName("江泰路").lineIds(Arrays.asList(2)).build(),
            MetroStation.builder().stationName("昌岗").lineIds(Arrays.asList(2, 8)).build(),
            MetroStation.builder().stationName("江南西").lineIds(Arrays.asList(2)).build(),
            MetroStation.builder().stationName("市二宫").lineIds(Arrays.asList(2)).build(),
            MetroStation.builder().stationName("海珠广场").lineIds(Arrays.asList(2, 6)).build(),
            // MetroStation.builder().stationName("公园前").lineIds(Arrays.asList(1, 2)).build(),
            MetroStation.builder().stationName("纪念堂").lineIds(Arrays.asList(2)).build(),
            MetroStation.builder().stationName("越秀公园").lineIds(Arrays.asList(2)).build(),
            MetroStation.builder().stationName("广州火车站").lineIds(Arrays.asList(2, 5)).build(),
            MetroStation.builder().stationName("三元里").lineIds(Arrays.asList(2)).build(),
            MetroStation.builder().stationName("飞翔公园").lineIds(Arrays.asList(2)).build(),
            MetroStation.builder().stationName("白云公园").lineIds(Arrays.asList(2)).build(),
            MetroStation.builder().stationName("白云文化广场").lineIds(Arrays.asList(2)).build(),
            MetroStation.builder().stationName("萧岗").lineIds(Arrays.asList(2, 5)).build(),
            MetroStation.builder().stationName("江夏").lineIds(Arrays.asList(2)).build(),
            MetroStation.builder().stationName("黄边").lineIds(Arrays.asList(2)).build(),
            MetroStation.builder().stationName("嘉禾望岗").lineIds(Arrays.asList(2, 3)).build());

    List<MetroStation> line3Stations =
        Arrays.asList(
            MetroStation.builder().stationName("番禺广场").lineIds(Arrays.asList(3, 18, 22)).build(),
            MetroStation.builder().stationName("市桥").lineIds(Arrays.asList(3)).build(),
            MetroStation.builder().stationName("汉溪长隆").lineIds(Arrays.asList(3, 7)).build(),
            MetroStation.builder().stationName("大石").lineIds(Arrays.asList(3)).build(),
            MetroStation.builder().stationName("厦滘").lineIds(Arrays.asList(3)).build(),
            MetroStation.builder().stationName("沥滘").lineIds(Arrays.asList(3, 26)).build(),
            MetroStation.builder().stationName("大塘").lineIds(Arrays.asList(3)).build(),
            MetroStation.builder().stationName("客村").lineIds(Arrays.asList(3, 8)).build(),
            MetroStation.builder().stationName("广州塔").lineIds(Arrays.asList(3, 23)).build(),
            MetroStation.builder().stationName("珠江新城").lineIds(Arrays.asList(3, 5)).build(),
            // MetroStation.builder().stationName("体育西路").lineIds(Arrays.asList(1, 3)).build(),
            MetroStation.builder().stationName("石牌桥").lineIds(Arrays.asList(3)).build(),
            MetroStation.builder().stationName("岗顶").lineIds(Arrays.asList(3)).build(),
            MetroStation.builder().stationName("华师").lineIds(Arrays.asList(3)).build(),
            MetroStation.builder().stationName("五山").lineIds(Arrays.asList(3)).build(),
            MetroStation.builder().stationName("天河客运站").lineIds(Arrays.asList(3, 6)).build(),
            MetroStation.builder().stationName("林和西").lineIds(Arrays.asList(3, 23)).build(),
            //    MetroStation.builder().stationName("广州东站").lineIds(Arrays.asList(1, 3)).build(),
            MetroStation.builder().stationName("燕塘").lineIds(Arrays.asList(3, 6)).build(),
            MetroStation.builder().stationName("梅花园").lineIds(Arrays.asList(3)).build(),
            MetroStation.builder().stationName("京溪南方医院").lineIds(Arrays.asList(3)).build(),
            MetroStation.builder().stationName("同和").lineIds(Arrays.asList(3)).build(),
            MetroStation.builder().stationName("永泰").lineIds(Arrays.asList(3)).build(),
            MetroStation.builder().stationName("白云大道北").lineIds(Arrays.asList(3)).build(),
            //  MetroStation.builder().stationName("嘉禾望岗").lineIds(Arrays.asList(2, 3, 14)).build(),
            MetroStation.builder().stationName("龙归").lineIds(Arrays.asList(3)).build(),
            MetroStation.builder().stationName("人和").lineIds(Arrays.asList(3)).build(),
            MetroStation.builder().stationName("高增").lineIds(Arrays.asList(3, 9)).build(),
            MetroStation.builder().stationName("机场南").lineIds(Arrays.asList(3)).build(),
            MetroStation.builder().stationName("机场北").lineIds(Arrays.asList(3)).build());

    List<MetroStation> line4Stations =
        Arrays.asList(
            MetroStation.builder().stationName("南沙客运港").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("南横").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("塘坑").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("大涌").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("广隆").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("飞沙角").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("金洲").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("蕉门").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("黄阁").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("黄阁汽车城").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("庆盛").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("东涌").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("低涌").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("海傍").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("石碁").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("官桥").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("新造").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("大学城南").lineIds(Arrays.asList(4, 7)).build(),
            MetroStation.builder().stationName("官洲").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("万胜围").lineIds(Arrays.asList(4, 8)).build(),
            MetroStation.builder().stationName("车陂南").lineIds(Arrays.asList(4, 5)).build(),
            MetroStation.builder().stationName("车陂").lineIds(Arrays.asList(4)).build(),
            MetroStation.builder().stationName("黄村").lineIds(Arrays.asList(4, 21)).build());

    System.out.println(
        line4Stations
            .stream()
            .map(o -> String.format("\"%s\"", o.getStationName()))
            .collect(Collectors.joining(",")));

    List<MetroStation> stations =
        Stream.of(line1Stations, line2Stations, line3Stations, line4Stations)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

    stations.stream().collect(Collectors.toMap(MetroStation::getStationName, Function.identity()));

    return MetroInfo.builder()
        .cityId(CityEnum.GUANGZHOU.getId())
        .cityName(CityEnum.GUANGZHOU.getName())
        .lines(lines)
        .stations(stations)
        .build();
  }

  public SingleLineMetroInfo singleLineMetroInfo(MetroInfoRequest request) {
    MetroInfo allCityMetroInfo = new MetroInfo();
    switch (CityEnum.fromId(request.getCityId())) {
      case BEIJING:
        allCityMetroInfo = beijingMetroInfo();
        break;
      case GUANGZHOU:
        allCityMetroInfo = guangzhouMetroInfo();
        break;
      default:
        break;
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
            .filter(o -> o.getLineIds().contains(line.getLineId()))
            .collect(Collectors.toMap(MetroStation::getStationName, Function.identity()));

    List<MetroStation> mainLineStations =
        line.getStationNames().stream().map(stationNameToStation::get).collect(Collectors.toList());

    List<MetroStation> allStations = new ArrayList<>(mainLineStations);
    Map<Integer, MetroLine> lineIdToLine =
        allCityMetroInfo
            .getLines()
            .stream()
            .collect(Collectors.toMap(MetroLine::getLineId, Function.identity()));

    // 计算单线路绘图的坐标信息.对于非环线,线路图为直线.站点的y坐标为canvas.height/2 x轴分成(站点个数+1)份.

    // 考虑x轴上最多有多少个站点.如果没有支线则有  line.getStationNames().size()个站点.否则从主线上找到分叉的位置.
    int xAxisStations = line.getStationNames().size();
    if (!CollectionUtils.isEmpty(line.getBranchLines())) {
      for (MetroBranchLine branchLine : line.getBranchLines()) {
        // 从这个站点开始分叉
        String splitStationName = branchLine.getBranchLineStationNames().get(0);
        int index = line.getStationNames().indexOf(splitStationName);
        xAxisStations =
            Math.max(xAxisStations, index + branchLine.getBranchLineStationNames().size());
      }
    }

    double stationXDistance = request.getCanvasWidth() / (xAxisStations + 1.0);

    int allLinesCount = 1;
    if (!CollectionUtils.isEmpty(line.getBranchLines())) {
      allLinesCount += line.getBranchLines().size();
    }

    double stationYDistance = request.getCanvasHeight() / (allLinesCount + 1.0);

    // 先计算主线上的站点位置
    for (int i = 0; i < mainLineStations.size(); i++) {
      mainLineStations.get(i).setXPos((i + 1) * stationXDistance);
      // 主线位于最下方(注意y轴正向向下
      mainLineStations
          .get(i)
          .setYPos(allLinesCount / (allLinesCount + 1.0) * request.getCanvasHeight());
    }
    // 再计算各支线站点位置.注意分叉站的位置已经算好.这里暂不考虑分支线路又汇入主线或者有交叉的情况

    if (!CollectionUtils.isEmpty(line.getBranchLines())) {
      for (int i = 0; i < line.getBranchLines().size(); i++) {

        MetroBranchLine branchLine = line.getBranchLines().get(i);
        MetroStation splitStation =
            stationNameToStation.get(
                line.getBranchLines().get(i).getBranchLineStationNames().get(0));
        branchLine.setSplitStation(splitStation);
        branchLine.setBranchLineStations(new ArrayList<>());
        for (int j = 1; j < branchLine.getBranchLineStationNames().size(); j++) {
          MetroStation station =
              stationNameToStation.get(branchLine.getBranchLineStationNames().get(j));

          // 支线位置位于分叉站右上方(注意y轴向下是正向)
          station.setXPos(splitStation.getXPos() + j * stationXDistance);
          station.setYPos(splitStation.getYPos() - (i + 1) * stationYDistance);

          branchLine.getBranchLineStations().add(station);
          allStations.add(station);
        }
      }
    }

    // 计算站点间连线数据.对于非环线,n个站点需要n-1个"线段"连接.每个线段为矩形,左右两边为凹进来的半圆. canvas绘图表现为arc+line+arc+line
    // 注意canvas绘图中原点是左上角且y轴方向向下
    List<MetroStationConnection> connections = new ArrayList<>();

    for (int i = 0; i < mainLineStations.size() - 1; i++) {
      connections.add(
          calculateConnection(
              line,
              mainLineStations.get(i),
              mainLineStations.get(i + 1),
              ConnectionTypeEnum.HORIZONTAL,
              request));
    }

    if (!CollectionUtils.isEmpty(line.getBranchLines())) {
      for (MetroBranchLine branchLine : line.getBranchLines()) {

        connections.add(
            calculateConnection(
                line,
                branchLine.getSplitStation(),
                branchLine.getBranchLineStations().get(0),
                ConnectionTypeEnum.LEFT_TO_TOP,
                request));

        // 非分叉站之间的连线
        for (int i = 0; i < branchLine.getBranchLineStations().size() - 1; i++) {
          connections.add(
              calculateConnection(
                  line,
                  branchLine.getBranchLineStations().get(i),
                  branchLine.getBranchLineStations().get(i + 1),
                  ConnectionTypeEnum.HORIZONTAL,
                  request));
        }
      }
    }

    // 如果有支线,支线的站点也拼上得到所有站点

    // 拼接换乘站相关信息 注意在此之前已经计算了各站点的绘图位置
    List<MetroStationConnection> stationTransfers = new ArrayList<>();
    List<MetroInfo.MetroStationTransferText> transferTexts = new ArrayList<>();
    for (MetroStation stationWithTransfer :
        allStations.stream().filter(o -> o.getLineIds().size() > 1).collect(Collectors.toList())) {

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
        default:
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
      }

      // 在point3下方10位置处添加文本
      for (int i = 0; i < otherLineIds.size(); i++) {
        transferTexts.add(
            MetroInfo.MetroStationTransferText.builder()
                .text(lineIdToLine.get(otherLineIds.get(i)).getLineName())
                .xPos(stationWithTransfer.getXPos())
                .yPos(point3.getY() + (i + 1) * request.getFontSize())
                .build());
      }
    }

    return SingleLineMetroInfo.builder()
        .lineId(line.getLineId())
        .lineName(line.getLineName())
        .color(line.getColor())
        .stations(allStations)
        .stationConnections(connections)
        .stationTransfers(stationTransfers)
        .transferTexts(transferTexts)
        .build();
  }

  private MetroStationConnection calculateConnection(
      MetroLine line,
      MetroStation first,
      MetroStation second,
      ConnectionTypeEnum connectionType,
      MetroInfoRequest request) {
    List<MetroStationConnectionLine> connectionLines = new ArrayList<>();
    Double theta = Math.asin(request.getConnectionLineWidth() / 2.0 / request.getStationRadius());
    switch (connectionType) {
      case HORIZONTAL:
        {
          MetroStationConnectionLine leftStationRightArc =
              MetroStationConnectionLine.builder()
                  .lineType(MetroStationLineTypeEnum.ARC.getIdentifier())
                  .arcCenterX(first.getXPos())
                  .arcCenterY(first.getYPos())
                  .radius(request.getStationRadius())
                  .arcStartAngle(theta)
                  .arcEndAngle(-theta)
                  .isCounterClockWise(true)
                  .build();
          // 挖掉半圆的矩形的顶部线段
          MetroStationConnectionLine topLine =
              MetroStationConnectionLine.builder()
                  .lineType(MetroStationLineTypeEnum.LINE.getIdentifier())
                  .lineStartX(first.getXPos() + request.getStationRadius() * Math.cos(theta))
                  .lineStartY(first.getYPos() - request.getStationRadius() * Math.sin(theta))
                  .lineEndX(second.getXPos() - request.getStationRadius() * Math.cos(theta))
                  .lineEndY(second.getYPos() - request.getStationRadius() * Math.sin(theta))
                  .build();

          MetroStationConnectionLine rightStationLeftArc =
              MetroStationConnectionLine.builder()
                  .lineType(MetroStationLineTypeEnum.ARC.getIdentifier())
                  .arcCenterX(second.getXPos())
                  .arcCenterY(second.getYPos())
                  .radius(request.getStationRadius())
                  .arcStartAngle(Math.PI + theta)
                  .arcEndAngle(Math.PI - theta)
                  .isCounterClockWise(true)
                  .build();

          MetroStationConnectionLine bottomLine =
              MetroStationConnectionLine.builder()
                  .lineType(MetroStationLineTypeEnum.LINE.getIdentifier())
                  .lineStartX(second.getXPos() - request.getStationRadius() * Math.cos(theta))
                  .lineStartY(second.getYPos() + request.getStationRadius() * Math.sin(theta))
                  .lineEndX(first.getXPos() + request.getStationRadius() * Math.cos(theta))
                  .lineEndY(first.getYPos() + request.getStationRadius() * Math.sin(theta))
                  .build();

          connectionLines =
              Arrays.asList(leftStationRightArc, topLine, rightStationLeftArc, bottomLine);
          break;
        }

      case LEFT_TO_TOP:
        {

          // 上弧起点(逆时针)
          Point point1 =
              new Point(
                  first.getXPos() + request.getStationRadius() * Math.sin(theta),
                  first.getYPos() - request.getStationRadius() * Math.cos(theta));

          // 上弧终点
          Point point2 =
              new Point(
                  first.getXPos() - request.getStationRadius() * Math.sin(theta), point1.getY());

          // 外侧曲线由一条垂线,一段顺时针弧,一条水平线构成.圆角矩形的圆角圆心.内外侧圆弧为同心圆弧.

          // point2,3 x相同. point1,4 x相同.
          Point point3 =
              new Point(
                  point2.getX(), second.getYPos() + request.getStationRadius() * Math.sin(theta));

          Point point4 =
              new Point(
                  point1.getX(), second.getYPos() - request.getStationRadius() * Math.sin(theta));
          // point4,5 y相同
          Point point5 =
              new Point(
                  second.getXPos() - request.getStationRadius() * Math.cos(theta), point4.getY());
          // point3,6 y相同
          Point point6 =
              new Point(
                  second.getXPos() - request.getStationRadius() * Math.cos(theta), point3.getY());

          // point7为内侧圆弧上点.这里先设置内弧半径为0.7,8,圆心重合
          Point point7 = new Point(point1.getX(), point3.getY());
          // point7为内侧圆弧左点
          Point point8 = new Point(point1.getX(), point3.getY());

          Point circleCenter = new Point(point1.getX(), point3.getY());
          Double outerLineRadius = circleCenter.getX() - point3.getX();
          Double innerLineRadius = 0.0;

          // 左边站点的上弧
          MetroStationConnectionLine leftStationTopArc =
              MetroStationConnectionLine.builder()
                  .lineType(MetroStationLineTypeEnum.ARC.getIdentifier())
                  .arcCenterX(first.getXPos())
                  .arcCenterY(first.getYPos())
                  .radius(request.getStationRadius())
                  .arcStartAngle(Math.PI * 3 / 2 + theta)
                  .arcEndAngle(Math.PI * 3 / 2 - theta)
                  .isCounterClockWise(true)
                  .build();

          // 外侧曲线的垂线
          MetroStationConnectionLine outerLineVerticalLine =
              MetroStationConnectionLine.constructFromPoints(point2, point3);
          // 外侧曲线的圆弧(point2->point3)

          MetroStationConnectionLine outerLineArc =
              MetroStationConnectionLine.builder()
                  .lineType(MetroStationLineTypeEnum.ARC.getIdentifier())
                  .arcCenterX(circleCenter.getX())
                  .arcCenterY(circleCenter.getY())
                  .radius(outerLineRadius)
                  .arcStartAngle(Math.PI)
                  .arcEndAngle(Math.PI * 3 / 2)
                  .build();

          // 外侧曲线的水平线
          MetroStationConnectionLine outerLineHorizontalLine =
              MetroStationConnectionLine.constructFromPoints(point4, point5);

          // 右边站点的左弧
          MetroStationConnectionLine rightStationLeftArc =
              MetroStationConnectionLine.builder()
                  .lineType(MetroStationLineTypeEnum.ARC.getIdentifier())
                  .arcCenterX(second.getXPos())
                  .arcCenterY(second.getYPos())
                  .radius(request.getStationRadius())
                  .arcStartAngle(Math.PI + theta)
                  .arcEndAngle(Math.PI - theta)
                  .isCounterClockWise(true)
                  .build();

          // 内侧曲线的水平线
          MetroStationConnectionLine innerLineHorizontalLine =
              MetroStationConnectionLine.constructFromPoints(point6, point7);

          MetroStationConnectionLine innerLineArc =
              MetroStationConnectionLine.builder()
                  .lineType(MetroStationLineTypeEnum.ARC.getIdentifier())
                  .arcCenterX(circleCenter.getX())
                  .arcCenterY(circleCenter.getY())
                  .radius(innerLineRadius)
                  .arcStartAngle(Math.PI * 3 / 2)
                  .arcEndAngle(Math.PI)
                  .isCounterClockWise(true)
                  .build();

          // 内侧曲线的水平线
          MetroStationConnectionLine innerLineVerticalLine =
              MetroStationConnectionLine.constructFromPoints(point8, point1);

          connectionLines =
              Arrays.asList(
                  leftStationTopArc,
                  outerLineVerticalLine,
                  outerLineArc,
                  outerLineHorizontalLine,
                  rightStationLeftArc,
                  innerLineHorizontalLine,
                  innerLineArc,
                  innerLineVerticalLine);

          break;
        }
      default:
        break;
    }

    // 左站点的右弧.逆时针 圆心为左站点中心.半径从请求中传入

    return MetroStationConnection.builder()
        .lines(connectionLines)
        .color(line.getColor())
        .name(String.format("%sto%s", first.getStationName(), second.getStationName()))
        .build();
  }
}
