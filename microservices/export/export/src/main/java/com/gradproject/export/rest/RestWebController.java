package com.gradproject.export.rest;

import com.gradproject.export.entity.DeviceInfo;
import com.gradproject.export.export.ExportService;
import com.gradproject.export.repo.MongoRepo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;

@Slf4j
@RestController
public class RestWebController {

  private final MongoRepo repo;
  private final ExportService es;

  public RestWebController(MongoRepo repo, ExportService es) {
    this.repo = repo;
    this.es = es;
  }

  @PostMapping(path = "/query", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> postQuery(@RequestBody QueryRequest qr) {
    log.trace("QueryRequest received {}", qr);
    log.trace(qr.getQuery());
    StringBuilder sb = new StringBuilder();
//    sb.append("{\"queryResults\":" + qs.getQuery(qr.getQuery()) + "}");
    return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
  }

  @GetMapping(path = "/health")
  public ResponseEntity<String> getHealth() {
    log.trace("health check succeeded");
    return new ResponseEntity<>("Export Service Healthy",HttpStatus.OK);
  }

  @GetMapping(path = "/genDevice")
  public ResponseEntity<String> genDevice(){
    log.trace("genning device");
    DeviceInfo di = new DeviceInfo(UUID.randomUUID(),"1", "now", "80", "81");
    repo.save(di);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping(path = "/getAllDevices")
  public ResponseEntity<Resource> getAllDevices(){
    log.trace("get all devices");
    List<DeviceInfo> devices = repo.findAll();
    File f = es.createCSV(devices);
    try {
      InputStreamResource resource = new InputStreamResource(new FileInputStream(f));
      return new ResponseEntity<>(resource,HttpStatus.OK);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(path = "/getDevice/{deviceId}")
  public ResponseEntity<Resource> getDevice(@PathVariable String deviceId){
    log.trace("getting device");
    List<DeviceInfo> devs = repo.findDeviceInfoByDeviceId(deviceId);
    File f = es.createCSV(devs);
    try {
      InputStreamResource resource = new InputStreamResource(new FileInputStream(f));
      return new ResponseEntity<>(resource,HttpStatus.OK);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(path = "/getDevices", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Resource> getDevices(@RequestBody Map<String, List<String>> deviceIdsToSearch){
    log.trace("getting device");
    for(String s: deviceIdsToSearch.get("devices")){
      log.trace(s);
    }
    List<String> deviceIds = deviceIdsToSearch.get("devices");
    List<DeviceInfo> devs = repo.findDeviceInfoByDeviceIdIn(deviceIds);
    File f = es.createCSV(devs);
    try {
      InputStreamResource resource = new InputStreamResource(new FileInputStream(f));
      return new ResponseEntity<>(resource,HttpStatus.OK);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

//  public String deviceOutput(List<DeviceInfo> devices){
//    log.trace("creating output");
//    StringBuilder sb = new StringBuilder();
//    Integer count = 0;
//    sb.append("{ \"devices\": [");
//    for(DeviceInfo device : devices){
//      if(count > 0){
//        sb.append(",");
//      }
//      sb.append("{\"id\": \""+device.id + "\", \"deviceId\": \"" + device.deviceId + "\",");
//      sb.append("\"timestamp\": \""+device.timestamp + "\", \"temperature\": \"" + device.temperature + "\",");
//      sb.append("\"humidity\": \""+device.humidity + "\"}");
//      count++;
//
//    }
//    sb.append("]}");
//    return sb.toString();
//  }

  @Data
  public static class QueryRequest implements Serializable {
    private String query;
  }
}
