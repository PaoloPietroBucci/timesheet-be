package timesheet.util.annotations;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class LogAspect {

  final protected Logger logger = LoggerFactory.getLogger(LogAspect.class);

  @Before("@annotation(HandleLog)")
  public void before(JoinPoint joinPoint) {
    logger.debug("[{}] BEFORE", joinPoint.getSignature());
    logger.debug("[{}] Args: {}", joinPoint.getSignature(), Arrays.stream(joinPoint.getArgs()).collect(Collectors.toList()));
  }

  @Around("@annotation(HandleLog)")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    Object proceed = joinPoint.proceed();
    logger.debug("[{}] EXECUTION TIME: {}ms", joinPoint.getSignature(), System.currentTimeMillis() - start);
    return proceed;
  }

  @After("@annotation(HandleLog)")
  public void after(JoinPoint joinPoint) {
    logger.debug("[{}] AFTER", joinPoint.getSignature());
  }

}
