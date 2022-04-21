package com.gradproject.export.rest;

import com.gradproject.export.entity.DeviceInfo;
import com.gradproject.export.export.ExportService;
import com.gradproject.export.repo.MongoRepo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResource;

import java.io.*;
import java.util.*;

@CrossOrigin
@Slf4j
@RestController
public class RestWebController {

  private final MongoRepo repo;
  private final ExportService es;

  public RestWebController(MongoRepo repo, ExportService es) {
    this.repo = repo;
    this.es = es;
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

  @GetMapping(path = "/test/getAllDevices", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public @ResponseBody byte[] getAllDevicesTest(){
    log.trace("get all devices Test");
    List<DeviceInfo> devices = repo.findAll();
    File f = es.createCSV(devices);
    File csvF = es.getCSV(f);
    log.trace(f.getPath());
    log.trace(f.getAbsolutePath());
    try {
      InputStream in = getClass().getResourceAsStream(f.getAbsolutePath());
      InputStreamResource resource = new InputStreamResource(new FileInputStream(csvF));
      FileInputStream fs = new FileInputStream(csvF);

      return fs.readAllBytes();
    }catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @GetMapping(path = "/getAllDevices")
  public ResponseEntity<Resource> getAllDevices(){
    log.trace("get all devices");
    List<DeviceInfo> devices = repo.findAll();
    File f = es.createCSV(devices);
    File f2 = es.getCSV(f);
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
}
