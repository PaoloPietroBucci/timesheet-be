package timesheet;

public final class ControllerRoute {

    public static final String BASE_PATH = "api/";

    public static final String PROJECT = BASE_PATH + "project";
    public static final String PROJECT_TYPE = BASE_PATH + "project-type";
    public static final String TIMETABLE = BASE_PATH + "timetable";
    public static final String USERS = BASE_PATH + "users";
    public static final String USERS_PROJECT = BASE_PATH + "users-project";
    public static final String TOTAL_HOUR_PER_PROJECT = BASE_PATH+"total-hour-per-project";
    public static final String HOUR_USER_PROJECT = BASE_PATH+"hour-user-project";




    public static final String ALL = "all";

    public static final String ALL_LITE = "all/lite";

    public static final String AUTHENTICATE = BASE_PATH + "authenticate";

    public static final String INSERT = "insert";

    public static final String PARAM_ID = "id";
    public static final String BY_ID = "/{" + PARAM_ID + "}";
    public static final String CURRENT_USER =BASE_PATH + "user";
    public static final String EXCEL ="excel";
    public static final String SUM = "sum";
    public static final String SUM_WEEKLY = "sum-weekly";

}
