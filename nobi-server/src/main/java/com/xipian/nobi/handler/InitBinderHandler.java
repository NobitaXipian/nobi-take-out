package com.xipian.nobi.handler;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author xipian
 * @date 2023/9/29
 */
@RestControllerAdvice
public class InitBinderHandler {

    @InitBinder
    public void dateTypeBinder(WebDataBinder webDataBinder){
        //往数据绑定器添加一个日期转化器
        webDataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        });
    }
}
