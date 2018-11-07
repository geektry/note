package com.geektry.note.framework;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Chaohang Fu
 */
@Component
public class HandlerInterceptorImpl implements HandlerInterceptor {

    @Value("${feign-client.basic-auth.string}")
    private String basicAuthString;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        boolean isMethodBeanAnnotated = handlerMethod.getBeanType().isAnnotationPresent(AuthRequired.class);
        boolean isMethodAnnotated = handlerMethod.getMethod().isAnnotationPresent(AuthRequired.class);
        if (!isMethodBeanAnnotated && !isMethodAnnotated) {
            return true;
        }

        boolean isAuthValid = this.isAuthValid(request.getHeader("authorization"));
        if (isAuthValid) {
            return true;
        }

        boolean isReturnTypeModelAndView = handlerMethod.getMethod().getReturnType().equals(ModelAndView.class);
        if (isReturnTypeModelAndView) {
            response.sendRedirect("/404");
            return false;
        }

        throw new ServiceRuntimeException(RuntimeExceptionMessage.AUTH_INVALID);
    }

    private boolean isAuthValid(String authorization) {
        return basicAuthString.equals(authorization);
    }
}
