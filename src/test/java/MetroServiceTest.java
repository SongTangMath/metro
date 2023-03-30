import com.ts.MetroService;
import com.ts.constants.CityEnum;
import com.ts.dto.CityWithMetroResponse;
import com.ts.dto.MetroInfo.SingleLineMetroInfo;
import com.ts.dto.MetroInfoRequest;
import com.ts.util.JsonUtil;
import org.junit.Test;

public class MetroServiceTest {
  private MetroService metroService = new MetroService();

  @Test
  public void testBeijingMetroInfo() {
    metroService.beijingMetroInfo();
  }

  @Test
  public void testAllCityMetroInfo() {
    CityWithMetroResponse allCityMetroInfo = metroService.allCityMetroInfo();
    System.out.println(JsonUtil.formatCamelCase(allCityMetroInfo));
  }

  @Test
  public void testBeijingLine1() {
    SingleLineMetroInfo singleLineMetroInfo =
        metroService.singleLineMetroInfo(
            MetroInfoRequest.builder().cityId(CityEnum.BEIJING.getId()).lineId(1).build());
    System.out.println(JsonUtil.formatCamelCase(singleLineMetroInfo));
  }

  @Test
  public void testBeijingLine2() {
    SingleLineMetroInfo singleLineMetroInfo =
        metroService.singleLineMetroInfo(
            MetroInfoRequest.builder().cityId(CityEnum.BEIJING.getId()).lineId(2).build());
    System.out.println(JsonUtil.formatCamelCase(singleLineMetroInfo));
  }

  @Test
  public void testBeijingLine4() {
    SingleLineMetroInfo singleLineMetroInfo =
        metroService.singleLineMetroInfo(
            MetroInfoRequest.builder().cityId(CityEnum.BEIJING.getId()).lineId(4).build());
    System.out.println(JsonUtil.formatCamelCase(singleLineMetroInfo));
  }

  @Test
  public void testBeijingLine5() {
    SingleLineMetroInfo singleLineMetroInfo =
        metroService.singleLineMetroInfo(
            MetroInfoRequest.builder().cityId(CityEnum.BEIJING.getId()).lineId(5).build());
    System.out.println(JsonUtil.formatCamelCase(singleLineMetroInfo));
  }

  @Test
  public void testBeijingLine6() {
    SingleLineMetroInfo singleLineMetroInfo =
        metroService.singleLineMetroInfo(
            MetroInfoRequest.builder().cityId(CityEnum.BEIJING.getId()).lineId(6).build());
    System.out.println(JsonUtil.formatCamelCase(singleLineMetroInfo));
  }

  @Test
  public void testBeijingLine7() {
    SingleLineMetroInfo singleLineMetroInfo =
        metroService.singleLineMetroInfo(
            MetroInfoRequest.builder().cityId(CityEnum.BEIJING.getId()).lineId(7).build());
    System.out.println(JsonUtil.formatCamelCase(singleLineMetroInfo));
  }

  @Test
  public void testBeijingLine8() {
    SingleLineMetroInfo singleLineMetroInfo =
        metroService.singleLineMetroInfo(
            MetroInfoRequest.builder().cityId(CityEnum.BEIJING.getId()).lineId(8).build());
    System.out.println(JsonUtil.formatCamelCase(singleLineMetroInfo));
  }

  @Test
  public void testBeijingLine13() {
    SingleLineMetroInfo singleLineMetroInfo =
        metroService.singleLineMetroInfo(
            MetroInfoRequest.builder().cityId(CityEnum.BEIJING.getId()).lineId(13).build());
    System.out.println(JsonUtil.formatCamelCase(singleLineMetroInfo));
  }

  @Test
  public void testGuangzhouMetroInfo() {
    metroService.guangzhouMetroInfo();
  }

  @Test
  public void testGuangzhouLine3() {
    SingleLineMetroInfo singleLineMetroInfo =
        metroService.singleLineMetroInfo(
            MetroInfoRequest.builder().cityId(CityEnum.GUANGZHOU.getId()).lineId(3).build());
    System.out.println(JsonUtil.formatCamelCase(singleLineMetroInfo));
  }
}
