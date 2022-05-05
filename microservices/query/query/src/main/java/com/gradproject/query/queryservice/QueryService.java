package com.gradproject.query.queryservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.UUID;

@Service
@Slf4j
public class QueryService {

  private final DataSource ds;

  @Autowired
  public QueryService(DataSource ds) {
    this.ds = ds;
  }

  public String getQuery(String query) {
    try {
      log.trace("getting query");
      Connection c = ds.getConnection();
      Statement s = c.createStatement();

      log.trace("before executing query");
      ResultSet rs = s.executeQuery(query);
      log.trace("after executing query");

      ResultSetMetaData rsMD = rs.getMetaData();
      Integer numCol = rsMD.getColumnCount();

      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("[");

      Integer count = 0;

      while (rs.next()) {
        if (count > 0) {
          stringBuilder.append(",");
        }
        stringBuilder.append("{");
        for (int i = 1; i <= numCol; i++) {
          if (i != 1) {
            stringBuilder.append(",");
          }

          stringBuilder.append("\"" + rsMD.getColumnLabel(i) + "\":");
          stringBuilder.append("\"" + rs.getString(i) + "\"");
        }

        stringBuilder.append("}");
        count += 1;
      }
      stringBuilder.append("]");
      log.trace(stringBuilder.toString());
      s.close();
      c.close();
      return stringBuilder.toString();
    } catch (SQLException e) {
      log.trace(e.getMessage());
      return "bad query";
    }
  }

  public void storeDevice(String device) {
    try {
      log.trace("device message received {}", device);

      log.trace("parsing device message");
      ObjectMapper mapper = new ObjectMapper();
      DeviceInfo di = mapper.readValue(device, DeviceInfo.class);

      log.trace("device info parsed: " + di.toString());
      Connection c = ds.getConnection();

      Statement s = c.createStatement();
      StringBuilder sb = new StringBuilder();
      sb.append("INSERT INTO deviceInfo(id, deviceId, time, humidity, temperature) VALUES (\'");
      sb.append(di.id + "\', \'" +  di.deviceId + "\', \'" + di.timestamp +"\', \'" + di.humidity + "\', \'" + di.temperature + "\');");
      log.trace(sb.toString());
      s.execute(sb.toString());
    } catch (SQLException | JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}
