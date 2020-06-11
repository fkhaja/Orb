package com.sin.orb.util;

import com.nimbusds.jose.util.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class CustomBeanUtils {

    public static <T> void copyPropsIgnoringNulls(T src, T target, String...ignoreProperties) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src, ignoreProperties));
    }

    private static <T> String[] getNullPropertyNames(T source, String[] ignoreProperties) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = ArrayUtils.concat(emptyNames.toArray(new String[0]), ignoreProperties);
        return emptyNames.toArray(result);
    }
}
