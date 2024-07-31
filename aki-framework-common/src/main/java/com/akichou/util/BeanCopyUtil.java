package com.akichou.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Bean Copy Utility
 *
 * @author Aki Chou
 * @date 2024/06/19 Wed.
 */
@Slf4j
public class BeanCopyUtil {

    private BeanCopyUtil() {}

    public static <V> V copyBean(Object source, Class<V> clazz) {

        V result ;

        try {
            result = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            log.error("Error occurred while copying bean from {} to {}",
                            source.getClass().getName(), clazz.getName(), e);

            throw new BeanCopyException("Error occurred while copying bean", e);
        }

        return result;
    }

    private static class BeanCopyException extends RuntimeException {

        public BeanCopyException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static <O, V> List<V> copyBeanList(List<O> source, Class<V> clazz) {

        return source.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList()) ;
    }
}
