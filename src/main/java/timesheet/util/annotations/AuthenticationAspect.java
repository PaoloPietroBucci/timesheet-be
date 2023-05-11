package timesheet.util.annotations;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthenticationAspect {

  final protected Logger logger = LoggerFactory.getLogger(AuthenticationAspect.class);

  /*@Before("@annotation(auth)")
  public void before(JoinPoint joinPoint, HandleAuthentication auth) throws ForbiddenException {
    logger.debug("[{}] BEFORE", joinPoint.getSignature());
    logger.debug("[{}] Required hierarchy => {}", joinPoint.getSignature(), auth.hierarchy());
    if (AuthUtil.getLoggedUser() != null) {
      Users user = AuthUtil.getLoggedUser();
      int hierarchy = RoleHierarchy.getHierarchy(user.getRole());
      logger.debug("[{}] Logged user => {} [{} - {}]", joinPoint.getSignature(), user.getId(), user.getRole(), hierarchy);
      if (hierarchy > auth.hierarchy()) {
        throw new ForbiddenException();
      }
    } else {
      logger.debug("[{}] No logged user", joinPoint.getSignature());
      throw new ForbiddenException();
    }
  }*/
}
