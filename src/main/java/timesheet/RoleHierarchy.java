package timesheet;

public final class RoleHierarchy {

    public static final int HIERARCHY_ALL = 9999;
    public static final int HIERARCHY_ADMIN = 0;
    public static final int HIERARCHY_MANGIATUTTO = 5;
    public static final int HIERARCHY_RESTAURANT_PLUS = 10;
    public static final int HIERARCHY_RESTAURANT = 20;
    public static final int HIERARCHY_RIDER = 30;
    public static final int HIERARCHY_CUSTOMER = 90;

    /**
     * @param role UsersRoleEnum
     * @return int
     */
    public static int getHierarchy(UsersRoleEnum role) {
        switch (role) {
            case ADMIN:
                return HIERARCHY_ADMIN;

            default:
                return HIERARCHY_ALL;
        }
    }
}
