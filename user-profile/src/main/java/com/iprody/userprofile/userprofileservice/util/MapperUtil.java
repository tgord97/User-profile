package com.iprody.userprofile.userprofileservice.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utility class for mapping objects using ModelMapper.
 */
@Configuration
public class MapperUtil {

    /**
     * Provides a bean for ModelMapper.
     *
     * @return An instance of ModelMapper.
     */
    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }

    /**
     * Converts a list of source objects to a list of target objects using the provided converter function.
     *
     * @param <R>       The type of target objects.
     * @param <E>       The type of source objects.
     * @param list      The list of source objects to be converted.
     * @param converter The converter function to convert each source object to a target object.
     * @return A list of converted target objects.
     */
    public static <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
        return list.stream().map(e -> converter.apply(e)).collect(Collectors.toList());
    }
}
