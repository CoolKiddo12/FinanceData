package com.irena.financial_data.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Static application context accessor.
 */
@Component
public class StaticContextAccessor {

    private static ApplicationContext context;

    @Autowired
    public StaticContextAccessor(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    /**
     * Gets the bean instance of the given class.
     * @param clazz the class.
     * @param <T> generic.
     * @return the bean instance of that class or null if not found.
     */
    public static <T> T getBean(Class<T> clazz) {
        return context != null ? context.getBean(clazz) : null;
    }
}