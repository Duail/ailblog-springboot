package com.brs.ailblog.util;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: ConstraintViolationException处理器
 * @Author: DC
 * @Date: created in 11:38 2018/6/6
 */
public class ConstraintViolationExceptionHandler {

    //处理多种异常
    public static String getMessage(ConstraintViolationException e) {
        List<String> msgList = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            msgList.add(constraintViolation.getMessage());
        }
        return StringUtils.join(msgList.toArray(), ";");
    }
}
