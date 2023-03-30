package com.ts;

import com.ts.dto.CityWithMetroResponse;
import com.ts.dto.MetroInfo;
import com.ts.dto.MetroInfo.SingleLineMetroInfo;
import com.ts.dto.MetroInfoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MetroController {
  private MetroService metroService;

  @Autowired
  public MetroController(MetroService metroService) {
    this.metroService = metroService;
  }

  @GetMapping("/allCityMetroInfo")
  public CityWithMetroResponse allCityMetroInfo() {
    try {
      return metroService.allCityMetroInfo();
    } catch (Exception e) {
      log.error("allCityMetroInfo failed", e);
      return null;
    }
  }

  @PostMapping("/metroInfo")
  public MetroInfo metroInfo(@RequestBody MetroInfoRequest request) {
    try {
      return metroService.metroInfo(request);
    } catch (Exception e) {
      log.error("metroInfo failed", e);
      return null;
    }
  }

  @PostMapping("/singleLineMetroInfo")
  public SingleLineMetroInfo singleLineMetroInfo(@RequestBody MetroInfoRequest request) {
    try {
      return metroService.singleLineMetroInfo(request);
    } catch (Exception e) {
      log.error("singleLineMetroInfo failed", e);
      return null;
    }
  }
}
