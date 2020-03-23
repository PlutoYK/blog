package com.ykcoding.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;


@ControllerAdvice
public class ControllerHandlerException {
//日志
private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView handlerException(HttpServletRequest request, Exception e) throws Exception{
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
            throw e;
        }
        logger.error("Request URL : {} , Exception : {}",request.getRequestURL(),e);
        String url = request.getRequestURI();
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",url);
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        return mv;
    }
}
