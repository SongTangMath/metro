import com.ts.MetroService;
import org.junit.Before;
import org.junit.Test;

public class MetroServiceTest {
  private MetroService metroService;

  @Before
  public void setUp() {
    metroService = new MetroService();
  }

  @Test
  public void testBeijingMetroInfo() {
    metroService.beijingMetroInfo();
  }
}
