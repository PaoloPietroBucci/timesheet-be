package timesheet.util.constants;
import timesheet.util.DateUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public final class AppConstants {

  public static final Integer MIN_WAITING = 100; // milliseconds

  public static final BigDecimal MIN_RECHARGE_AMOUNT = BigDecimal.valueOf(10);
  public static final BigDecimal COMMISSION_RECHARGE_AMOUNT = BigDecimal.valueOf(.25);
  public static final BigDecimal EXTRA_RECHARGE_AMOUNT = BigDecimal.valueOf(.25);

  public static final Integer REWARD_DAYS = 7;

  public static final String LANGUAGE_FILTER = "languageFilter";
  public static final String LANGUAGE_FILTER_PARAM = "language";

  public static final String MQTT_RECHARGE_TOPIC = "waithero-recharge-";
  public static final String MQTT_RESTAURANT_TOPIC = "waithero-restaurant-carts-";
  public static final String MQTT_RESTAURANT_DEVICE_TOPIC = "waithero-restaurant-device-carts-";
  public static final String MQTT_USER_TOPIC = "waithero-user-";

  public static final String API_DOCS_URI = "api-docs";
  public static final String BATCH = "batch";
  public static final String SWAGGER_URI = "swagger";
  public static final String WEBHOOK = "webhook";

  public static final DecimalFormat INTEGER_FORMATTER = new DecimalFormat("0");
  public static final DecimalFormat AMOUNT_FORMATTER = new DecimalFormat("#0.00");

  public static final String DEFAULT_LANGUAGE = "en";
  public static final String DEFAULT_WHITELABEL = "1";

  /**
   * @return String
   */
  public static String getDefaultPassword() {
    return "WaitHero@1234!";
  }

  /**
   * @return String
   */
  public static String getPassword() {
    return RandomStringUtils.randomAlphanumeric(16);
  }

  /**
   * @return ObjectMapper
   */
  public static ObjectMapper getObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setDateFormat(DateUtil.SDF_MOSQUITTO)
        .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .enable(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS);
    return mapper;
  }

  /**
   * @return ObjectMapper
   */
  public static ObjectMapper getMongoDBObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return mapper;
  }

}
