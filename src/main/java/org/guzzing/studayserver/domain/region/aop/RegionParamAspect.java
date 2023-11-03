package org.guzzing.studayserver.domain.region.aop;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.guzzing.studayserver.domain.region.model.Region;
import org.guzzing.studayserver.global.exception.RegionException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RegionParamAspect {

    private static final String SIDO = "sido";
    private static final String SIGUNGU = "sigungu";
    private static final String UPMYEONDONG = "upmyeondong";

    @Before(value = "@annotation(org.guzzing.studayserver.domain.region.aop.ValidSido)")
    public void validateRegionSidoParameter(JoinPoint joinpoin) {
        MethodSignatureInfo methodSignatureInfo = getMethodSignatureInfo(joinpoin);

        Object sido = getParameterValue(methodSignatureInfo.args(), methodSignatureInfo.parameterNames(),
                methodSignatureInfo.parameterTypes(), SIDO);

        validateRegionParam(sido, SIDO);
    }

    @Before(value = "@annotation(org.guzzing.studayserver.domain.region.aop.ValidSigungu)")
    public void validateRegionSigunguParameter(JoinPoint joinPoint) {
        MethodSignatureInfo methodSignatureInfo = getMethodSignatureInfo(joinPoint);

        Object sigungu = getParameterValue(methodSignatureInfo.args(), methodSignatureInfo.parameterNames(),
                methodSignatureInfo.parameterTypes(), SIGUNGU);

        validateRegionParam(sigungu, SIGUNGU);
    }

    @Before(value = "@annotation(org.guzzing.studayserver.domain.region.aop.ValidUpmyeondong)")
    public void validateRegionUpmyeondongParameter(JoinPoint joinPoint) {
        MethodSignatureInfo methodSignatureInfo = getMethodSignatureInfo(joinPoint);

        Object upmyeondong = getParameterValue(methodSignatureInfo.args(), methodSignatureInfo.parameterNames(),
                methodSignatureInfo.parameterTypes(), UPMYEONDONG);

        validateRegionParam(upmyeondong, UPMYEONDONG);
    }

    private void validateRegionParam(Object sido, String regionParamName) {
        List<String> regionPostfixList = switch (regionParamName) {
            case SIDO -> Region.BASE_REGION_SIDO;
            case SIGUNGU -> Region.SIGUNGU_POSTFIX;
            case UPMYEONDONG -> Region.UPMYEONDONG_POSTFIX;
            default -> List.of();
        };

        if (sido != null) {
            boolean isInvalidSidoParameter = regionPostfixList.stream()
                    .noneMatch(postfix -> String.valueOf(sido).contains(postfix));

            if (isInvalidSidoParameter) {
                throw new RegionException("올바르지 않은 시도명이거나, 제공하지 않는 지역입니다.");
            }
        }
    }

    private MethodSignatureInfo getMethodSignatureInfo(JoinPoint joinpoin) {
        MethodSignature methodSignature = (MethodSignature) joinpoin.getSignature();

        Object[] args = joinpoin.getArgs();
        String[] parameterNames = methodSignature.getParameterNames();
        Class<?>[] parameterTypes = methodSignature.getParameterTypes();

        return new MethodSignatureInfo(args, parameterNames, parameterTypes);
    }

    private Object getParameterValue(
            Object[] args,
            String[] parameterNames,
            Class<?>[] parameterTypes,
            String regionUnitParamName
    ) {
        Object regionUnit = null;

        for (int i = 0; i < args.length; i++) {
            if (Objects.equals(parameterNames[i], regionUnitParamName)
                    && Objects.equals(parameterTypes[i], String.class)) {
                regionUnit = args[i];
            }
        }
        return regionUnit;
    }

    private record MethodSignatureInfo(Object[] args, String[] parameterNames, Class<?>[] parameterTypes) {

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            MethodSignatureInfo that = (MethodSignatureInfo) o;
            return Arrays.equals(args, that.args) && Arrays.equals(parameterNames, that.parameterNames)
                    && Arrays.equals(parameterTypes, that.parameterTypes);
        }

        @Override
        public int hashCode() {
            int result = Arrays.hashCode(args);
            result = 31 * result + Arrays.hashCode(parameterNames);
            result = 31 * result + Arrays.hashCode(parameterTypes);
            return result;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", MethodSignatureInfo.class.getSimpleName() + "[", "]")
                    .add("args=" + Arrays.toString(args))
                    .add("parameterNames=" + Arrays.toString(parameterNames))
                    .add("parameterTypes=" + Arrays.toString(parameterTypes))
                    .toString();
        }
    }

}
