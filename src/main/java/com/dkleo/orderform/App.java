package com.dkleo.orderform;

import java.text.SimpleDateFormat;

/**
 * Hello world!
 *
 */
public class App 
{
    public static String now()
      {
          SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyyy");
          java.util.Date date = new java.util.Date();
          return sdf.format(date);
      }
}
