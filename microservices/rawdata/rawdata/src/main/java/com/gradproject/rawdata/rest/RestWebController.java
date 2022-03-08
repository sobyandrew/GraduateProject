package com.gradproject.rawdata.rest;

import com.gradproject.rawdata.entity.DeviceInfo;
import com.gradproject.rawdata.repo.MongoRepo;
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

@Slf4j
@RestController
public class RestWebController {

  private final MongoRepo repo;

  public RestWebController(MongoRepo repo) {
    this.repo = repo;
  }

  @GetMapping(path = "/genDevice")
  public ResponseEntity<String> genDevice(){
    log.trace("genning device");
    DeviceInfo di = new DeviceInfo("1234", "now", "device");
    repo.save(di);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Data
  public static class QueryRequest implements Serializable {
    private String query;
  }
}
