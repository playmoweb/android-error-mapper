package com.playmoweb.errormapper;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Interface allowing multiple ShowError annotations
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface ShowErrors {
    String[] value();
}
