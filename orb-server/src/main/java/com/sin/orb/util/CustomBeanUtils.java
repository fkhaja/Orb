package com.sin.orb.util;

import lombok.NonNull;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

public class CustomBeanUtils {

    public static <T> void populate(@NonNull T bean, @NonNull Map<String, Object> props, Class<T> srcClass, String... ignoreProperties) {
        removeIgnoredProps(props, ignoreProperties);

        props.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(srcClass, k);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, v);
            }
        });
    }

    private static void removeIgnoredProps(Map<String, Object> target, String[] ignoreProperties) {
        if (ArrayUtils.isNotEmpty(ignoreProperties)) {
            for (String prop : ignoreProperties) {
                target.remove(prop);
            }
        }
    }
}