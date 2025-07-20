package provided.util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DaoBase {
  protected void startTransaction(Connection conn) throws SQLException {
    conn.setAutoCommit(false);
  }

  protected void commitTransaction(Connection conn) throws SQLException {
    conn.commit();
  }

  protected void rollbackTransaction(Connection conn) throws SQLException {
    try {
      conn.rollback();
    } catch (Exception e) {
    }
  }

  protected <T> void setParameter(PreparedStatement stmt, int parameterIndex, T value,
      Class<T> classType) throws SQLException {
    if (null == value) {
      stmt.setNull(parameterIndex, java.sql.Types.NULL);
    } else {
      stmt.setObject(parameterIndex, value);
    }
  }

  protected Integer getLastInsertId(Connection conn, String table) throws SQLException {
    String sql = "SELECT LAST_INSERT_ID()";

    try (Statement stmt = conn.createStatement()) {
      try (ResultSet rs = stmt.executeQuery(sql)) {
        if (rs.next()) {
          return rs.getInt(1);
        }
        return null;
      }
    }
  }

  protected <T> T extract(ResultSet rs, Class<T> classType) throws SQLException {
    try {
      T obj = classType.getConstructor().newInstance();
      ResultSetMetaData rsmd = rs.getMetaData();

      for (int i = 1; i <= rsmd.getColumnCount(); i++) {
        String columnName = rsmd.getColumnName(i);
        Object value = rs.getObject(i);
        setField(obj, columnName, value, classType);
      }

      return obj;
    } catch (Exception e) {
      throw new RuntimeException("Unable to extract object from result set", e);
    }
  }

  protected <T> List<T> extract(ResultSet rs, Class<T> classType, boolean b)
      throws SQLException {
    List<T> list = new ArrayList<>();

    while (rs.next()) {
      list.add(extract(rs, classType));
    }

    return list;
  }

  private <T> void setField(T obj, String columnName, Object value, Class<T> classType) {
    String fieldName = toCamelCase(columnName);

    try {
      Field field = classType.getDeclaredField(fieldName);
      field.setAccessible(true);
      
      if(null != value) {
        field.set(obj, value);
      }
    } catch (Exception e) {
      System.out.println("Unable to set field " + fieldName + " on object of type "
          + classType.getSimpleName());
    }
  }

  private String toCamelCase(String str) {
    StringBuilder sb = new StringBuilder();
    boolean capitalizeNext = false;

    for (char c : str.toCharArray()) {
      if (c == '_') {
        capitalizeNext = true;
      } else {
        if (capitalizeNext) {
          sb.append(Character.toUpperCase(c));
          capitalizeNext = false;
        } else {
          sb.append(c);
        }
      }
    }

    return sb.toString();
  }
}
