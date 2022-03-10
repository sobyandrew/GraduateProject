package com.gradproject.export.export;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gradproject.export.entity.DeviceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ExportService {

  private final ObjectMapper mapper;

  public ExportService() {
    mapper = new ObjectMapper();
  }

  public File createCSV(List<DeviceInfo> devices) {
    File f = new File("device.json");
    log.trace("creating csv");
    try {
      mapper.writeValue(f, devices);
    } catch (IOException e) {
      log.trace(e.getMessage());
      return f;
    }
    log.trace("done writing file");
    return f;
    //        JsonNode jTree = mapper.readTree(devices);
  }
}
