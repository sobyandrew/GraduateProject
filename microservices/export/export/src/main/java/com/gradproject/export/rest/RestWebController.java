package com.gradproject.export.rest;

import com.gradproject.export.entity.DeviceInfo;
import com.gradproject.export.repo.MongoRepo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
public class RestWebController {

  private final MongoRepo repo;

  public RestWebController(MongoRepo repo) {
    this.repo = repo;
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

  @GetMapping(path = "/getDevice")
  public ResponseEntity<String> getDevice(){
    log.trace("getting device");

    Optional dev = repo.findById("1234");

    log.trace(dev.get().toString());
    return new ResponseEntity<>(dev.get().toString(),HttpStatus.OK);
  }

  @Data
  public static class QueryRequest implements Serializable {
    private String query;
  }
}
