package com.gradproject.query.rest;

import com.gradproject.query.queryservice.QueryService;
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

@Slf4j
@RestController
public class RestWebController {

  private final QueryService qs;

  public RestWebController(QueryService qs) {
    this.qs = qs;
  }

  @PostMapping(path = "/query", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> postQuery(@RequestBody QueryRequest qr) {
    log.trace("QueryRequest received {}", qr);
    log.trace(qr.getQuery());
    StringBuilder sb = new StringBuilder();
    sb.append("{\"queryResults\":" + qs.getQuery(qr.getQuery()) + "}");
    return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
  }

  @GetMapping(path = "/health")
  public ResponseEntity<String> getHealth() {
    log.trace("health check succeeded");
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping(path = "/device", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> postTempData(@RequestBody String device) {
    log.trace("Device Data to store {}", device);
    qs.storeDevice(device);
    return new ResponseEntity<>("stored data", HttpStatus.OK);
  }

  @Data
  public static class QueryRequest implements Serializable {
    private String query;
  }
}
