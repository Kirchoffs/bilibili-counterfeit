package com.syh.bilibili.service.handler;

import com.syh.bilibili.domain.JsonResponse;
import com.syh.bilibili.domain.exception.ConditionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonGlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResponse<String> commonExceptionHandler(HttpServletRequest request, Exception exception) {
        String errorMsg = exception.getMessage();
        if (exception instanceof ConditionException) {
            String errorCode = ((ConditionException) exception).getCode();
            return new JsonResponse<>(errorCode, errorMsg);
        } else {
            return new JsonResponse<>("500", errorMsg);
        }
    }
}
