package com.ecommerce.catalog.business.util;

import java.util.UUID;

public class CommonUtil {
  public static String generateUUID() {
    return UUID.randomUUID().toString();
  }
  public static String getCurrentDateTime(){
    return java.time.LocalDateTime.now().toString();
  }
}
