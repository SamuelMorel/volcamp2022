package org.volcamp.api.utils;

import javax.enterprise.inject.spi.CDI;

/**
 * This class Allows resources mutualism
 */
public final class BeanUtils {

    private BeanUtils() {
    }

    /**
     * Allows getting a bean from the class given in parameter
     * This method avoid the @Inject annotation loop when a Class A reference a Class B and the opposite
     *
     * @param classToGet The bean we want to get the instance
     * @param <T>        The generic type of the bean
     * @return The bean instance
     */
    public static <T> T injectBean(Class<T> classToGet) {
        return CDI.current().select(classToGet).get();
    }
}
