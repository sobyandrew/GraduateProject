package com.gradproject.normalize.normalizeservice;

import com.gradproject.normalize.entity.DeviceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class NormalizeService {

  private final DataSource ds;

  @Autowired
  public NormalizeService(DataSource ds) {
    this.ds = ds;
  }

  public void storeDevices(List<DeviceInfo> devices) {
    try {
      Connection c = ds.getConnection();
      Statement s = c.createStatement();

      StringBuilder sb;
      for (DeviceInfo di : devices) {
        sb = new StringBuilder();

        sb.append("INSERT INTO deviceInfo(id, deviceId, time, humidity, temperature) VALUES (\'");
        sb.append(
            di.id
                + "\', \'"
                + di.deviceId
                + "\', \'"
                + di.timestamp
                + "\', \'"
                + di.humidity
                + "\', \'"
                + di.temperature
                + "\');");

        s.addBatch(sb.toString());
      }
      log.trace("executing batch");
      s.executeBatch();
      s.close();
      c.close();
    } catch (SQLException e) {
      log.trace(e.getMessage());
    }

  }
  @EventListener(ApplicationReadyEvent.class)
  public void runOnStartup() throws ExecutionException, InterruptedException {
    log.trace("initialize db");
    try {
      Connection c = ds.getConnection();
      Statement s = c.createStatement();
      String createSql = "CREATE table if not exists deviceInfo\n" +
              "(\n" +
              "    id          uuid        not null,\n" +
              "    deviceId    varchar(50) not null,\n" +
              "    time        varchar(50) not null,\n" +
              "    humidity    varchar(5)  not null,\n" +
              "    temperature varchar(5)  not null,\n" +
              "    PRIMARY KEY (id)\n" +
              ");";
      s.executeUpdate(createSql);
      log.trace("create table");
      log.trace("\n" + createSql);
      s.close();
      c.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }


  }
}
