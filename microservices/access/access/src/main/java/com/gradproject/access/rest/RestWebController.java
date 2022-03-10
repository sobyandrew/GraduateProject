package com.gradproject.access.rest;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class RestWebController {
  private final RestTemplate rt;

  public RestWebController() {
    this.rt = new RestTemplate();
  }

  @PostMapping(path = "/query", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> postQuery(@RequestBody QueryRequest qr) {
    log.trace("QueryRequest received {}", qr);
    String s = rt.postForObject("http://localhost:8081/query", qr, String.class);
    log.trace(s);
    return new ResponseEntity<>(s, HttpStatus.OK);
  }

  @GetMapping(path = "/export/getAllDevices")
  public ResponseEntity<Resource> getAllDevicesExport() {
    log.trace("get all devices Export");
    Resource resp = rt.getForObject("http://localhost:8082/getAllDevices", Resource.class);
    return new ResponseEntity<>(resp, HttpStatus.OK);
  }

  @GetMapping(path = "/export/getDevice/{deviceId}")
  public ResponseEntity<Resource> getOneDevicesExport(@PathVariable String deviceId) {
    log.trace("get device Export for dev: " + deviceId);
    Resource resc = rt.getForObject("http://localhost:8082/getDevice/" + deviceId, Resource.class);
    return new ResponseEntity<>(resc, HttpStatus.OK);
  }

  @PostMapping(path = "/export/getDevices", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Resource> getDevices(
      @RequestBody Map<String, List<String>> deviceIdsToSearch) {
    log.trace("getting device");
    Resource resc =
        rt.postForObject("http://localhost:8082/getDevices", deviceIdsToSearch, Resource.class);
    return new ResponseEntity<>(resc, HttpStatus.OK);
  }

  @GetMapping(path = "/alarm/getAlarms")
  public ResponseEntity<String> getAlarms() {
    log.trace("get alarms from alarm service");
    return new ResponseEntity<>(
            rt.getForObject("http://localhost:8083/getAlarms", String.class), HttpStatus.OK);
  }

  @GetMapping(path = "/query/health")
  public ResponseEntity<String> getQueryHealth() {
    log.trace("get raw data from export");
    return new ResponseEntity<>(
        rt.getForObject("http://localhost:8081/health", String.class), HttpStatus.OK);
  }

  @GetMapping(path = "/export/health")
  public ResponseEntity<String> getExportHealth() {
    log.trace("get export health");
    return new ResponseEntity<>(
        rt.getForObject("http://localhost:8082/health", String.class), HttpStatus.OK);
  }

  @GetMapping(path = "/alarm/health")
  public ResponseEntity<String> getAlarmHealth() {
    log.trace("get alarm health");
    return new ResponseEntity<>(
            rt.getForObject("http://localhost:8083/health", String.class), HttpStatus.OK);
  }

  @Data
  public static class QueryRequest implements Serializable {
    private String query;
  }
}
