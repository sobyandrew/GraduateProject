package com.gradproject.normalize.normalizeservice;

import com.gradproject.normalize.entity.DeviceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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

      StringBuilder sb = new StringBuilder();
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
    } catch (SQLException e) {
      log.trace(e.getMessage());
    }
  }
}
