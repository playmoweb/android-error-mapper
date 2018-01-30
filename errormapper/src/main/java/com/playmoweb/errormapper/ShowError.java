package com.playmoweb.errormapper;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * ShowError annotation
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface ShowError {
    String value();
}
