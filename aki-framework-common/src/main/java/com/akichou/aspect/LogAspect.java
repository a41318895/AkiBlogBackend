package com.akichou.aspect;

import com.akichou.annotation.SystemLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * LogAspect (AOP -> log handle)
 *
 * @author Aki Chou
 * @date 2024/06/23 Sun.
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.akichou.annotation.SystemLog)")
    public void point() {
    }

    @Around("point()")
    public Object printLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object result ;
        try{
            handleBefore(proceedingJoinPoint);
            result = proceedingJoinPoint.proceed() ;
            handleAfter(result) ;
        }finally {
            log.info("========== End =========={}", System.lineSeparator());
        }

        return result ;
    }

    private void handleBefore(ProceedingJoinPoint proceedingJoinPoint) throws JsonProcessingException {

        // Get Request Entity
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes() ;
        assert servletRequestAttributes != null;
        HttpServletRequest request = servletRequestAttributes.getRequest();

        // Get SystemLog Annotation Entity
        SystemLog systemLog = getSystemLog(proceedingJoinPoint) ;

        // Transfer ProceedingJoinPoint getArgs() LIST -> JSON format
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonFormattedArgs = objectMapper.writeValueAsString(proceedingJoinPoint.getArgs()) ;

        log.info("========== Start ==========") ;
        log.info("URL               : {}", request.getRequestURL()) ;
        log.info("BusinessName      : {}", systemLog.businessName()) ;
        log.info("HTTP Method       : {}", request.getMethod()) ;
        log.info("Class Method      : {}.{}", proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                                              proceedingJoinPoint.getSignature().getName() ) ;
        log.info("IP                : {}", request.getRemoteHost()) ;
        log.info("Request Args      : {}", jsonFormattedArgs) ;
    }

    private SystemLog getSystemLog(ProceedingJoinPoint proceedingJoinPoint) {

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature() ;

        return methodSignature.getMethod().getAnnotation(SystemLog.class);
    }

    private void handleAfter(Object result) throws JsonProcessingException {

        // Transfer proceedingJoinPoint proceed() result object -> JSON format
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonFormattedResultObject = objectMapper.writeValueAsString(result) ;

        log.info("RESPONSE          : {}", jsonFormattedResultObject) ;
    }
}
