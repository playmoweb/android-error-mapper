package com.playmoweb.errormapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ErrorEntityMapper is a utils class which allows usage
 * of @ShowError and @ShowErrors annotations on methods in views.
 * <p>
 * These two annotations flag any methods to be call if the key is found in the check() method
 */
public class ErrorEntityMapper<V> {
    public static class MethodCallFailedException extends RuntimeException {
        MethodCallFailedException(String s, Throwable throwable) {
            super(s, throwable);
        }
    }

    private V view;
    private final HashMap<String, List<Method>> bindings = new HashMap<>();

    /**
     * Build the errorMapper through reflection of the view
     *
     * @return A mapped key-method object
     */
    public static <V> ErrorEntityMapper build(final V view) {
        final ErrorEntityMapper errorEntityMapper = new ErrorEntityMapper();
        final Method[] methods = view.getClass().getMethods();

        for (final Method method : methods) {
            if (method.isAnnotationPresent(ShowError.class)) {
                errorEntityMapper.addBinding(method.getAnnotation(ShowError.class).value(), method);
            } else if (method.isAnnotationPresent(ShowErrors.class)) {
                for (final String k : method.getAnnotation(ShowErrors.class).value()) {
                    errorEntityMapper.addBinding(k, method);
                }
            }
        }

        errorEntityMapper.view = view;
        return errorEntityMapper;
    }

    /**
     * Add a binding safely
     */
    private void addBinding(final String key, final Method method) {
        List<Method> list = bindings.get(key);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(method);
        bindings.put(key, list);
    }

    /**
     * Private constructor to enforce user to use the build() method
     */
    private ErrorEntityMapper() {
    }

    /**
     * Check if a binding exists. If yes, call every methods associated
     */
    public <T> void check(final HashMap<String, T> errorKeyAndMessage) {
        if (errorKeyAndMessage == null || errorKeyAndMessage.size() == 0) {
            return;
        }

        for (final Map.Entry<String, T> entry : errorKeyAndMessage.entrySet()) {
            if (bindings.containsKey(entry.getKey())) {
                for (final Method m : bindings.get(entry.getKey())) {
                    try {
                        m.invoke(view, entry.getKey(), entry.getValue());
                    } catch (IllegalAccessException e) {
                        throw new MethodCallFailedException("IllegalAccessException: Calling method failed for key => " + entry.getKey(), e);
                    } catch (InvocationTargetException e) {
                        throw new MethodCallFailedException("InvocationTargetException: Calling method failed for key => " + entry.getKey(), e);
                    }
                }
            }
        }
    }

    /**
     * Release resources
     */
    public void detach() {
        bindings.clear();
        view = null;
    }
}
