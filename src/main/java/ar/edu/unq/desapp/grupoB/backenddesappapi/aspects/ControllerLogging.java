package ar.edu.unq.desapp.grupoB.backenddesappapi.aspects;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Aspect
@Component
public class ControllerLogging {
    private static final Logger logger = LogManager.getLogger(ControllerLogging.class);


    @Pointcut("within (@org.springframework.web.bind.annotation.RestController *) "+
                "&& within(ar.edu.unq.desapp.grupoB.backenddesappapi.webservices..*)")
    public void allRequest() {
    }


    @Before("allRequest()")
    public void beforeRequest(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String log = "Method: " +signature.getName() + " >>>";
        for(Object arg : joinPoint.getArgs()){
            log+= "  ARG: "+ arg;
        }
        LogManager.getLogger(signature.getDeclaringTypeName()).info(log);
    }
    @AfterReturning(pointcut = "allRequest()", returning = "entity")
    public void afterRequest(JoinPoint joinPoint, ResponseEntity<?> entity) {
        String log = "Status: "+ entity.getStatusCode();
        if(entity.hasBody()) {
            log += "\n Body: " + entity.getBody();
        }
        LogManager.getLogger(joinPoint.getSignature().getDeclaringTypeName()).info(log);
    }
    @AfterThrowing(pointcut = "allRequest()", throwing = "exception")
    public void afterFailing(JoinPoint joinPoint, Exception exception){
        logger.error("ERROR: "+exception.getMessage());
    }

    @Around("within (@org.springframework.web.bind.annotation.RestController *)"+
            "&& within(ar.edu.unq.desapp.grupoB.backenddesappapi.webservices..*)")
    public Object logAroundController(ProceedingJoinPoint joinPoint) throws Throwable {
       logger.log(Level.INFO,"----------------------------------------------------------------------------------------------------------------------------------------------------------------");
       Instant start = Instant.now();
       Object returnValue= joinPoint.proceed();
       Instant finish = Instant.now();
       long timeElapsed = Duration.between(start,finish).toMillis();
       logger.log(Level.INFO, "Time taken: "+new SimpleDateFormat("mm:ss:SSS").format(new Date(timeElapsed)));
       logger.log(Level.INFO,"----------------------------------------------------------------------------------------------------------------------------------------------------------------");
       return returnValue;
    }
}



