package ar.edu.unq.desapp.grupoB.backenddesappapi.aspects;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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

    @Around("within (@org.springframework.web.bind.annotation.RestController *)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Instant start = Instant.now();
        Object returnValue= joinPoint.proceed();
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start,finish).toMillis();

        logger.log(Level.INFO,"----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        logger.log(Level.INFO,"Request for {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                                                                  joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        logger.log(Level.INFO, "Time taken: "+new SimpleDateFormat("mm:ss:SSS").format(new Date(timeElapsed)));
        logger.log(Level.INFO,"----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        return returnValue;
    }
}
