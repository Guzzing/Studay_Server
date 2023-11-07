package org.guzzing.studayserver.global.aop;

import jakarta.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    private static final String FORMAT = "invoke method : {} - {} ({}) / elapsed time : {}";
    private static final double MILLI_SECOND_TO_SECOND_UNIT = 0.001;
    private static final double MAX_AFFORDABLE_TIME = 3;

    @Around("execution(* org.guzzing.studayserver.domain.*.controller.*Controller.*(..))")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis(); //메소드를 호출하기 전 시간
        Object proceed = joinPoint.proceed(); //aop에 적용된 메소드를 실제로 호출한다.
        long endTime = System.currentTimeMillis(); //메소드를 호출한 후 시간

        double elapsedTime = (endTime - startTime) * MILLI_SECOND_TO_SECOND_UNIT;

        String className = joinPoint.getSignature().getDeclaringTypeName(); //jointPoint를 통해 얻어온 클래스 정보
        String methodName = joinPoint.getSignature().getName();//jointPoint를 통해 얻어온 메소드 정보

        String requestUrl = getRequestUrl();

        if (elapsedTime > MAX_AFFORDABLE_TIME) {
            log.warn(FORMAT, className, methodName, elapsedTime, requestUrl);

            return proceed; //메소드의 결과를 반환해주는 이유는 aop가 프록시로 구현되기 때문이다. 프록시 객체가 원본 객체와 동일하게 결과를 반환해야 하기 때문에 여기서 원본 객체의 결과(proceed)를 return해줘야 프록시 객체가 원본 객체와 동일하게 작동할 수 있다.
        }

        log.info(FORMAT, className, methodName, elapsedTime, requestUrl);

        return proceed;
    }

    private String getRequestUrl() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();

        return MessageFormat.format("{0}{1}", requestURI, queryString);
    }
}