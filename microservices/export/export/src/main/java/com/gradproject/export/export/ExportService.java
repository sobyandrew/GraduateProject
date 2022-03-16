package com.gradproject.export.export;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
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

  public File getCSV(File jsonFile){
    File f = new File("deviceExport.csv");
    try {
      JsonNode jsonTree = new ObjectMapper().readTree(jsonFile);
      CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
      JsonNode firstObject = jsonTree.elements().next();
      firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);} );
      CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

      CsvMapper csvMapper = new CsvMapper();
      csvMapper.writerFor(JsonNode.class)
              .with(csvSchema)
              .writeValue(f, jsonTree);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return f;
  }
}
