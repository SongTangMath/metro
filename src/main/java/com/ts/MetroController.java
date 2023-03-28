package com.ts;

import com.ts.MetroInfo.SingleLineMetroInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
