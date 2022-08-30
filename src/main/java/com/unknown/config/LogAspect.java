package com.unknown.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
@Aspect
@Profile("local")
public class LogAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void onPost() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void onPut() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void onDelete() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void onGet() {}

    @Around("onPost()||onPut()||onDelete()||onGet()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        Class<?> clazz = joinPoint.getTarget().getClass();

        Object result = null;
        StringBuilder sb = new StringBuilder();
        // Gson gson = new Gson();
        Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

        sb.append("\n").append("URL ----------------------------------------");
        sb.append("\n").append(this.getRequestUrl(joinPoint, clazz));
        sb.append("\n").append("Header -------------------------------------");
        sb.append("\n").append(this.getHeader(request));
        sb.append("\n").append("prev parameers -----------------------------");
        sb.append("\n").append(gson.toJson(this.getParams(joinPoint)));

        try {
            result = joinPoint.proceed(joinPoint.getArgs());
            return result;
        } finally {
            sb.append("\n").append("next parameers -----------------------------");
            sb.append("\n").append(gson.toJson(this.getParams(joinPoint)));
            sb.append("\n").append("response ----------------------------------");
            sb.append("\n").append(gson.toJson(result));
            sb.append("\n").append("-------------------------------------------");

            System.out.println(sb.toString());
        }
    }

    private String getHeader(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        String key;
        int cnt = 0;

        while (headerNames.hasMoreElements()) {
            key = headerNames.nextElement();
            if (cnt > 0) {
                sb.append("\n");
            }
            sb.append(String.format("%s: %s", key, request.getHeader(key)));
            cnt++;
        }

        return sb.toString();
    }

    private Map<String, Object> getParams(JoinPoint joinPoint) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            if (args[i] instanceof HttpServletRequest) {
                continue;
            } else if (args[i] instanceof HttpServletResponse) {
                continue;
            } else if (args[i] instanceof Errors) {
                continue;
            }
            params.put(parameterNames[i], args[i]);
        }
        return params;
    }

    private String getRequestUrl(JoinPoint joinPoint, Class<?> clazz) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        RequestMapping requestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
        String baseUrl = requestMapping.value()[0];

        String url = Stream.of(GetMapping.class, PutMapping.class, PostMapping.class, PatchMapping.class, DeleteMapping.class, RequestMapping.class)
                        .filter(mappingClass -> method.isAnnotationPresent(mappingClass))
                        .map(mappingClass -> this.getUrl(method, mappingClass, baseUrl))
                        .findFirst().orElse(null);

        return url;
    }

    private String getUrl(Method method, Class<? extends Annotation> annotationClass, String baseUrl) {
        Annotation annotation = method.getAnnotation(annotationClass);
        String[] value;
        String httpMethod = null;
        try {
            value = (String[])annotationClass.getMethod("value").invoke(annotation);
            httpMethod = (annotationClass.getSimpleName().replace("Mapping", "")).toUpperCase();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
        return String.format("[%s] %s%s", httpMethod, baseUrl, value.length > 0 ? value[0] : "");
    }
}