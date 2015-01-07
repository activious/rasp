package util.sql;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;

public class DefaultItemCreator<T> implements ItemCreator<T> {
   private Class<? extends T> itemClass;

   private final Map<String, String> mapOverrides;

   public DefaultItemCreator(Class<? extends T> itemClass) {
      this(itemClass, null);
   }

   public DefaultItemCreator(Class<? extends T> itemClass,
                             Map<String, String> mapOverrides) {
      this.itemClass = itemClass;
      this.mapOverrides = mapOverrides;
   }

   @Override
   public T createFrom(ResultSet rs) throws SQLException {
      try {
         T item = itemClass.newInstance();

         PropertyDescriptor[] pds =
               Introspector.getBeanInfo(itemClass).getPropertyDescriptors();

         ResultSetMetaData meta = rs.getMetaData();

         for (int i = 1; i <= meta.getColumnCount(); i++) {
            String f = meta.getColumnLabel(i);
            String p = (mapOverrides != null ? mapOverrides.get(f) : null);
            if (p == null)
               p = f;

            for (PropertyDescriptor pd : pds) {
               if (pd.getName().equalsIgnoreCase(p)) {
                  pd.getWriteMethod().invoke(item,
                                             getFieldFrom(rs, f,
                                                          pd.getWriteMethod()));
                  break;
               }
            }
         }

         return item;
      } catch (InstantiationException |
               IllegalAccessException |
               IntrospectionException |
               SQLException |
               IllegalArgumentException |
               InvocationTargetException e) {
         throw new SQLException("Item creation failed", e);
      }
   }

   private Object getFieldFrom(ResultSet rs, String f, Method m) throws SQLException,
                                                                InstantiationException,
                                                                IllegalAccessException {
      Class<?> c = m.getParameterTypes()[0];

      if (c == int.class)
         return rs.getInt(f);

      if (c == Integer.class) {
         int v = rs.getInt(f);
         return (rs.wasNull() ? null : v);
      }

      if (c == long.class)
         return rs.getLong(f);

      if (c == Long.class) {
         long v = rs.getLong(f);
         return (rs.wasNull() ? null : v);
      }

      if (c == short.class)
         return rs.getShort(f);

      if (c == Short.class) {
         short v = rs.getShort(f);
         return (rs.wasNull() ? null : v);
      }

      if (c == double.class)
         return rs.getDouble(f);

      if (c == Double.class) {
         double v = rs.getDouble(f);
         return (rs.wasNull() ? null : v);
      }

      if (c == float.class)
         return rs.getFloat(f);

      if (c == Float.class) {
         float v = rs.getFloat(f);
         return (rs.wasNull() ? null : v);
      }

      if (c == boolean.class)
         return rs.getBoolean(f);

      if (c == Boolean.class) {
         boolean v = rs.getBoolean(f);
         return (rs.wasNull() ? null : v);
      }

      if (c == byte.class)
         return rs.getByte(f);

      if (c == Byte.class) {
         byte v = rs.getByte(f);
         return (rs.wasNull() ? null : v);
      }

      if (c == byte[].class)
         return rs.getBytes(f);

      if (c == String.class)
         return (m.isAnnotationPresent(UsingNCS.class)
               ? rs.getNString(f)
               : rs.getString(f));

      if (c == BigDecimal.class)
         return rs.getBigDecimal(f);

      if (c == Blob.class)
         return rs.getBlob(f);

      if (c == Clob.class)
         return (m.isAnnotationPresent(UsingNCS.class)
               ? rs.getNClob(f)
               : rs.getClob(f));

      if (c == NClob.class)
         return rs.getNClob(f);

      if (c == Ref.class)
         return rs.getRef(f);

      if (c == RowId.class)
         return rs.getRowId(f);

      if (c == SQLXML.class)
         return rs.getSQLXML(f);

      if (c == Array.class)
         return rs.getArray(f);

      if (c == URL.class)
         return rs.getURL(f);

      if (c == Reader.class)
         return (m.isAnnotationPresent(UsingNCS.class)
               ? rs.getNCharacterStream(f)
               : rs.getCharacterStream(f));

      if (c == InputStream.class)
         return (m.isAnnotationPresent(UsingBinaryStream.class)
               ? rs.getBinaryStream(f)
               : rs.getAsciiStream(f));

      if (c == Date.class)
         return (m.isAnnotationPresent(UsingCalendar.class)
               ? rs.getDate(f, m.getAnnotation(UsingCalendar.class).value()
                                .newInstance())
               : rs.getDate(f));

      if (c == Time.class)
         return (m.isAnnotationPresent(UsingCalendar.class)
               ? rs.getTime(f, m.getAnnotation(UsingCalendar.class).value()
                                .newInstance())
               : rs.getTime(f));

      if (c == Timestamp.class)
         return (m.isAnnotationPresent(UsingCalendar.class)
               ? rs.getTimestamp(f, m.getAnnotation(UsingCalendar.class)
                                     .value().newInstance())
               : rs.getTimestamp(f));

      if (c == Object.class)
         return rs.getObject(f);
      else
         return rs.getObject(f, c);
   }
}
