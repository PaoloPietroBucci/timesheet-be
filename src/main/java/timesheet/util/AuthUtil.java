package timesheet.util;

import timesheet.orm.entity.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    final protected static Logger logger = LoggerFactory.getLogger(AuthUtil.class);

    /**
     * @return Users
     */
    public static Users getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object obj = authentication.getPrincipal();
        if (obj instanceof String) {
            return null;
        }
        return (Users) obj;
    }

    /**
     * @return Long
     */
    public static Long getLoggedUserId() {
        Users logged = getLoggedUser();
        return logged != null ? logged.getId() : null;
    }

    /**
     * @return Long
     */
    public static Long getAdminUserId() {
        return 1L;
    }

    /**
     * @return Long
     */
    public static Users getAnonymousUser() {
        Users anonymous = new Users();
        anonymous.setId(2L);
        return anonymous;
    }

    /**
     * @return Long
     */
    public static Long getAnonymousUserId() {
        return 2L;
    }

}
