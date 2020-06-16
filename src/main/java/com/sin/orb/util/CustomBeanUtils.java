package com.sin.orb.util;

import org.springframework.util.ReflectionUtils;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.Map;

public class CustomBeanUtils {

    public static <T> void copyPropertiesFromMap(@NotNull T source, @NotNull Map<String, Object> target,
                                                 @NotNull Class<T> srcClass, String... ignoreProperties) {
        for (String prop : ignoreProperties) {
            target.remove(prop);
        }

        target.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(srcClass, k);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, source, v);
            }
        });
    }
}
