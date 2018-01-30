package com.playmoweb.errormapper;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Error Mapper monkey tests
 */
public class ErrorEntityMapperTest {

    @Test
    public void callAnnotatedMethod1WithListParam() {
        ErrorEntityMapper mapper = ErrorEntityMapper.build(new AnnotatedClass());
        HashMap<String, List<String>> kvp = new HashMap<>();

        List<String> list = new ArrayList<>();
        list.add("callAnnotatedMethod_arg1");
        list.add("callAnnotatedMethod_arg2");

        kvp.put("method1", list);
        try {
            mapper.check(kvp);
            Assert.assertEquals(false, true); // should never be here
        } catch (Exception e ){
            AnnotatedClass.AnnotatedClassException exception = extractExceptionFromCauses(e);
            AnnotatedClass.Payload<List<String>> p = exception.payload;
            Assert.assertEquals(p.key, "method1");
            Assert.assertEquals(p.arg.size(), list.size());
            Assert.assertEquals(p.arg.get(0), "callAnnotatedMethod_arg1");
            Assert.assertEquals(p.arg.get(1), "callAnnotatedMethod_arg2");
        }

        mapper.detach();
    }

    @Test
    public void callAnnotatedMethod2WithStringParam() {
        ErrorEntityMapper mapper = ErrorEntityMapper.build(new AnnotatedClass());
        HashMap<String, String> kvp = new HashMap<>();
        kvp.put("method2", "callAnnotatedMethod2");

        try {
            mapper.check(kvp);
            Assert.assertEquals(false, true); // should never be here
        } catch (Exception e ){
            AnnotatedClass.AnnotatedClassException exception = extractExceptionFromCauses(e);
            AnnotatedClass.Payload<String> p = exception.payload;
            Assert.assertEquals(p.key, "method2");
            Assert.assertEquals(p.arg, "callAnnotatedMethod2");
        }

        mapper.detach();
    }

    @Test
    public void callAnnotatedInterfaceMethod4() {
        ErrorEntityMapper mapper = ErrorEntityMapper.build(new AnnotatedClass());
        HashMap<String, String> kvp = new HashMap<>();
        kvp.put("method4", "callAnnotatedInterfaceMethod4");

        try {
            mapper.check(kvp);
            Assert.assertEquals(false, true); // should never be here
        } catch (Exception e ){
            AnnotatedClass.AnnotatedClassException exception = extractExceptionFromCauses(e);
            AnnotatedClass.Payload<String> p = exception.payload;
            Assert.assertEquals(p.key, "method4");
            Assert.assertEquals(p.arg, "callAnnotatedInterfaceMethod4");
        }

        mapper.detach();
    }

    // Extract if it exist the cause exception as AnnotatedClassException
    private AnnotatedClass.AnnotatedClassException extractExceptionFromCauses(Exception e){
        Throwable t = e.getCause();
        while(t != null) {
            if(t instanceof AnnotatedClass.AnnotatedClassException){
                return (AnnotatedClass.AnnotatedClassException) t;
            }
            t = t.getCause();
        }
        return null;
    }
}
