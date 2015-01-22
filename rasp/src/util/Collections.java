package util;

import java.util.Collection;

public class Collections {
   public static boolean containsAny(Collection<?> collection, Object object) {
      for (Object o : collection) {
         if (o.equals(object))
            return true;
      }
      
      return false;
   }
}
