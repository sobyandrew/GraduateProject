package com.gradproject.alarm.rest;

import com.gradproject.alarm.entity.AlarmEntity;
import com.gradproject.alarm.service.AlarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@Slf4j
@RestController
public class RestWebController {

    private final AlarmService as;

    @Autowired
    public RestWebController(AlarmService as) {
        this.as = as;
    }

    @GetMapping(path = "/health")
    public ResponseEntity<String> getHealth() {
        log.trace("health check succeeded");
        return new ResponseEntity<>("Alarm Service Healthy", HttpStatus.OK);
    }


    @GetMapping(path = "/genAlarm")
    public ResponseEntity<String> getGenAlarm() {
        log.trace("genning alarm");
        AlarmEntity ae = new AlarmEntity();
        ae.setDeviceId("1");
        ae.setTimestamp("now");
        ae.setHumidity("80");
        ae.setTemperature("55");
        ae.setSeverity("severe");
        ae.setId(UUID.randomUUID().toString());
        as.handleAlarm(ae);
        return new ResponseEntity<>("alarm generated", HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(path = "/getAlarms")
    public ResponseEntity<List<AlarmEntity>> getMostRecentAlarms() {
        log.trace("health check succeeded");
        return new ResponseEntity<>(as.returnAlarms(), HttpStatus.OK);
    }
}
