import com.ts.CityEnum;
import com.ts.JsonUtil;
import com.ts.MetroInfo.SingleLineMetroInfo;
import com.ts.MetroInfoRequest;
import com.ts.MetroService;
import org.junit.Test;

public class MetroServiceTest {
  private MetroService metroService = new MetroService();

  @Test
  public void testBeijingMetroInfo() {
    metroService.beijingMetroInfo();
  }

  @Test
  public void testBeijingLine1MetroInfo() {
    SingleLineMetroInfo singleLineMetroInfo =
        metroService.singleLineMetroInfo(
            MetroInfoRequest.builder().cityId(CityEnum.BEIJING.getId()).lineName("1号线八通线").build());
    System.out.println(JsonUtil.formatCamelCase(singleLineMetroInfo));
  }

  @Test
  public void testBeijingLine13MetroInfo() {
    SingleLineMetroInfo singleLineMetroInfo =
        metroService.singleLineMetroInfo(
            MetroInfoRequest.builder().cityId(CityEnum.BEIJING.getId()).lineName("13号线").build());
    System.out.println(JsonUtil.formatCamelCase(singleLineMetroInfo));
  }
}
