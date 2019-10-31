package com.logicline.sample.commons;

import com.logicline.sample.agent.model.Measurement;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
  private final static Logger log = LoggerFactory.getLogger(Util.class);
  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private static final DateTimeFormatter formatter =
      DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

  public static DateTime toW3CTimeString(String time) {

    return formatter.parseDateTime(time);

  }

  public static long toMillis(String time) throws ParseException {
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    Date reference = dateFormat.parse("00:00:00");
    Date date = dateFormat.parse(time);
    long seconds = (date.getTime() - reference.getTime()) / 1000L;
    return seconds;
  }

  public static long difference(String endTime, String beginTime) throws ParseException {
    Date eT = null;
    Date bT = null;
    eT = DATE_FORMAT.parse(endTime);
    bT = DATE_FORMAT.parse(beginTime);
    DateTime eDate = new DateTime(eT);
    DateTime bDate = new DateTime(bT);
    return Seconds.secondsBetween(bDate, eDate).getSeconds();
  }

  public static Measurement getRandomMeasurement(){
    Measurement measurement = new Measurement();
    measurement.setUnit("S");
    measurement.setValue(Math.random());
    return measurement;
  }
}
