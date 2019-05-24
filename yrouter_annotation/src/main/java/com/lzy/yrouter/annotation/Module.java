package com.lzy.yrouter.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Lizhengyu
 * @description
 * @date 2019/4/19 11:12
 **/
@Retention(RetentionPolicy.CLASS)
public @interface Module {
    String value();
}
