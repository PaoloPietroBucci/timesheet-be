package timesheet.util.annotations;

import timesheet.RoleHierarchy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HandleAuthentication {

    int hierarchy() default RoleHierarchy.HIERARCHY_ALL;

}
