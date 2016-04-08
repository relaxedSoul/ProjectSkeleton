package com.relaxedsoul.projectskeleton.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface ActivityRef {
    int resource() default 0;

    int toolbarResId() default 0;

    boolean busEnabled() default false;

    boolean knifeEnabled() default false;

    boolean navigation() default false;


    boolean isHomeAsUp() default false;
}
