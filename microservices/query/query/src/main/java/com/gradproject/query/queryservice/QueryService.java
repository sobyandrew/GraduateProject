package com.gradproject.query.queryservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

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
      return stringBuilder.toString();
    } catch (SQLException e) {
      log.trace(e.getMessage());
      return "bad query";
    }
  }
}
