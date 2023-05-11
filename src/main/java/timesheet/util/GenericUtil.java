package timesheet.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

public class GenericUtil {

    @SuppressWarnings("unchecked")
    public static <K, T, V> boolean isNullOrEmpty(T input) {
        if (input == null) {
            return true;
        } else if (input instanceof Collection) {
            return ((Collection<T>) input).isEmpty();
        } else if (input instanceof Map) {
            return ((Map<K, V>) input).isEmpty();
        } else if (input instanceof String) {
            return input.equals("");
        }
        return false;
    }

    public static <T> boolean isNotNullOrEmpty(T input) {
        return !isNullOrEmpty(input);
    }

    public static boolean isNullOrZero(Number input) {
        return input == null || input.equals(0);
    }

    public static boolean isNotNullAndZero(Number input) {
        return !isNullOrZero(input);
    }

    public static boolean isNullOrZero(BigDecimal input) {
        return input == null || input.compareTo(BigDecimal.ZERO) == 0;
    }

    public static boolean isNotNullAndZero(BigDecimal input) {
        return !isNullOrZero(input);
    }

    public static String like(String input) {
        return "%" + input + "%";
    }

    public static String replace(String pattern, Object... placeHolders) {
        return MessageFormat.format(pattern, placeHolders);
    }

    public static String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public static String formatNumber(BigDecimal input) {
        return new DecimalFormat("#0.00").format(input != null ? input : BigDecimal.ZERO);
    }

    public static String getFileName(String vat, String name, String ext) {
        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()) + "_" + vat + "_" + name + "." + ext;
    }

    public static String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }
}
