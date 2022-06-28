package ar.edu.unq.desapp.grupoB.backenddesappapi.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Configuration
public class Logging {

    private static final Logger logger = LogManager.getLogger(Logging.class);

    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        logger.debug("Request for {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                                                                  joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        Instant start = Instant.now();
        Object returnValue= joinPoint.proceed();
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start,finish).toMillis();
        logger.debug("Time taken: ",new SimpleDateFormat("mm:ss:SSS").format(new Date(timeElapsed)));
        logger.debug("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        return returnValue;
    }
}
