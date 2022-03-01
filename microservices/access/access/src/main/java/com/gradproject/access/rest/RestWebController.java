package com.gradproject.access.rest;

import com.sun.net.httpserver.Headers;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

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

  @Data
  public static class QueryRequest implements Serializable {
    private String query;
  }
}
