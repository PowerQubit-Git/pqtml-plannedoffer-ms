package pt.tml.plannedoffer.aspects;

import lombok.extern.flogger.Flogger;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Flogger
@Aspect
@Component
//@ConditionalOnExpression("${aspect.enabled:true}")
public class LogExecutionTimeAspect
{

    //@Pointcut("execution(public * *(..))")

    @Around(value = "@annotation(LogExecutionTime)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable
    {

        long startTime = System.currentTimeMillis();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String onCompletedLabel = signature.getMethod().getAnnotation(LogExecutionTime.class).completed();
        String onStartedLabel = signature.getMethod().getAnnotation(LogExecutionTime.class).started();
        String methodName = signature.getName();
        String className = signature.getDeclaringTypeName();

        if (!StringUtils.isEmpty(onStartedLabel))
        {
            log.atInfo().log(onStartedLabel);
        }

        Object proceed = joinPoint.proceed();

        float elapsed = System.currentTimeMillis() - startTime;

        String timeUnit = "ms";

        if (elapsed > 60000)
        {
            elapsed /= 60000;
            timeUnit = "min";
        }

        final DecimalFormat f = new DecimalFormat("0.#");
        String timeString = ". $$$$$ Execution time: " + f.format(elapsed) + timeUnit;

        String sOut;

        if (!StringUtils.isEmpty(onCompletedLabel))
        {
            sOut = onCompletedLabel + timeString;
        }
        else
        {
            sOut = className + " : " + methodName + timeString;
        }

        log.atInfo().log(sOut);
        return proceed;
    }
}