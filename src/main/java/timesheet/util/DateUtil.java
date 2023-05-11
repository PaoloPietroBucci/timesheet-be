package timesheet.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

  public static final SimpleDateFormat SDF_MOSQUITTO = new SimpleDateFormat("yyyy-MM-dd HH:mm");
  public static final SimpleDateFormat SDF_PONYU = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
  public static final SimpleDateFormat SDF_FE = new SimpleDateFormat("yyyy-MM-dd HH:mm");
  public static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy HH:mm");
  public static final SimpleDateFormat SDF_COUPON = new SimpleDateFormat("yyMMddHH");
  public static final SimpleDateFormat SDF_DAY = new SimpleDateFormat("dd/MM/yyyy");
  public static final SimpleDateFormat SDF_DAY_EN = new SimpleDateFormat("MM/dd/yyyy");
  public static final SimpleDateFormat SDF_LOG = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
  public static final SimpleDateFormat SDF_SQL_DAY = new SimpleDateFormat("yyyy-MM-dd");
  public static final SimpleDateFormat SDF_TIME = new SimpleDateFormat("HH:mm");
  public static final SimpleDateFormat SDF_EAT_IN_TIME = new SimpleDateFormat("yyyy/MM/dd");

  /**
   * @param dateInput Date
   * @param type      int
   * @param qty       int
   * @return Date
   */
  public static Date add(Date dateInput, int type, int qty) {
    Calendar cal = Calendar.getInstance();
    if (dateInput != null) {
      cal.setTime(dateInput);
    }
    cal.add(type, qty);
    return cal.getTime();
  }

	/**
	 * @param input Date
	 * @return Date
	 */
	public static Date endOfDay(Date input) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(input);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

  /**
   * @param input String
   * @return int
   */
  public static String hours(int input) {
    if (input < 10) {
      return "0" + input;
    }
    return "" + input;
  }

  /**
   * @param input String
   * @return int
   */
  public static String quarter(String input) {
    int minutes = Integer.parseInt(input);
    if (minutes < 15) {
      return "15";
    } else if (minutes < 30) {
      return "30";
    } else if (minutes < 45) {
      return "45";
    } else {
      return "00";
    }
  }

  /**
   * @param minutes int
   * @return int
   */
  public static int quarter(int minutes) {
    if (minutes < 15) {
      return 15;
    } else if (minutes < 30) {
      return 30;
    } else if (minutes < 45) {
      return 45;
    } else {
      return 0;
    }
  }

  /**
   * @param minutes int
   * @return int
   */
  public static int five(int minutes) {
    if (minutes < 5) {
      return 5;
    } else if (minutes < 10) {
      return 10;
    } else if (minutes < 15) {
      return 15;
    } else if (minutes < 20) {
      return 20;
    } else if (minutes < 25) {
      return 25;
    } else if (minutes < 30) {
      return 30;
    } else if (minutes < 35) {
      return 35;
    } else if (minutes < 40) {
      return 40;
    } else if (minutes < 45) {
      return 45;
    } else if (minutes < 50) {
      return 50;
    } else if (minutes < 55) {
      return 55;
    } else {
      return 0;
    }
  }

	/**
	 * @param input Date
	 * @return Date
	 */
	public static Date startOfDay(Date input) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(input);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

  /**
   * @param input Date
   * @return String
   */
  public static String weekday(Date input) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(input);
    int weekday = calendar.get(Calendar.DAY_OF_WEEK);
    switch (weekday) {
      case 1:
        return "SU";
      case 2:
        return "MO";
      case 3:
        return "TU";
      case 4:
        return "WE";
      case 5:
        return "TH";
      case 6:
        return "FR";
      case 7:
      default:
        return "SA";
    }
  }

  /**
   * @param sdf   SimpleDateFormat
   * @param input String
   * @return Date
   */
  public static Date parse(SimpleDateFormat sdf, String input) {
    try {
      return sdf.parse(input);
    } catch (ParseException ignored) {
    }
    return new Date();
  }

}
