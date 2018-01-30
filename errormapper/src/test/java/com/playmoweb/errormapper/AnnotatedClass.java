package com.playmoweb.errormapper;

import java.util.List;

/**
 * Test class only
 */
public class AnnotatedClass implements AnnotatedInterface {

    public static class Payload<T> {
        public final String key;
        public final T arg;

        public Payload(String key, T arg) {
            this.key = key;
            this.arg = arg;
        }
    }

    public static class AnnotatedClassException extends RuntimeException {
        public final Payload payload;

        public AnnotatedClassException(Payload payload) {
            this.payload = payload;
        }
    }

    @ShowError("method1")
    public String method1(String key, List<String> params) {
        throw new AnnotatedClassException(new Payload<>(key, params));
    }

    @ShowError("method2")
    public String method2(String key, String param1) {
        throw new AnnotatedClassException(new Payload<>(key, param1));
    }

    @ShowErrors({"method1"})
    public String method3(String key, List<String> params) {
        throw new AnnotatedClassException(new Payload<>(key, params));
    }

    // overrides without annotation

    @ShowError("method4")
    @Override
    public String method4(String key, String value) {
        throw new AnnotatedClassException(new Payload<>(key, value));
    }

    @Override
    public String method5(String key, String value) {
        // won't work !
        throw new AnnotatedClassException(new Payload<>(key, value));
    }
}
